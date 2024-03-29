/*
 * Copyright 2015-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.dbflute.solr.cbean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.dbflute.helper.HandyDate;
import org.dbflute.solr.entity.dbmeta.SolrDBMeta;
import org.dbflute.util.DfStringUtil;

/**
 * Solrクエリ文字列生成クラス<br />
 * qフィールドに投げる検索クエリ文字列を生成します
 * @author FreeGen
 */
public class SolrQueryBuilder {

    private static final String VALUE_SEPARATOR = "_";

    private static final Pattern BOOLEAN_PHRASE_PART = Pattern.compile("\"(.+?)\"");

    /** 日時フォーマッタ。 */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.000'Z'");

    private static final int PAD_LIMIT = 8192;

    /** セット範囲検索のワイルドカード */
    private static final String SET_RANGE_SEARCH_RIGHT_WILDCARD = "?????";

    /** セット範囲検索のワイルドカード */
    private static final String SET_RANGE_SEARCH_LEFT_WILDCARD = "\\*";

    /**
     * グループにラップする。
     * @param query query
     * @return グループ化したクエリ文字列
     */
    public static String wrapGroupQuery(String query) {
        return "(" + query + ")";
    }

    /**
     * 否定グループにラップする。
     * @param query query
     * @param asterisk アスタリクスを条件に入れる場合、<code>true</code>
     * @return グループ化したクエリ文字列
     */
    public static String wrapNotGroupQuery(String query, boolean asterisk) {
        if (asterisk) {
            return "(NOT " + query + " AND *:*)";
        }
        return "(NOT " + query + ")";
    }

    /**
     * クエリリストをANDでつなげる。
     * @param queryList query list
     * @return クエリリストをANDでつなげたクエリ文字列
     */
    public static String concatEachCondition(List<String> queryList) {
        return concatEachCondition(queryList, SolrQueryLogicalOperator.AND);
    }

    /**
     * クエリリストを論理演算でつなげる。
     * @param queryList query list
     * @param operator operator
     * @return クエリリストを論理演算でつなげたクエリ文字列
     */
    public static String concatEachCondition(List<String> queryList, SolrQueryLogicalOperator operator) {
        StringBuilder queryBuilder = new StringBuilder();
        if (isNotEmptyStrict(queryList)) {
            for (int i = 0; i < queryList.size(); i++) {
                if (i != 0) {
                    queryBuilder.append(" ");
                    queryBuilder.append(operator.name());
                    queryBuilder.append(" ");
                }
                queryBuilder.append(queryList.get(i));
            }
        } else {
            // 何もクエリがない場合
            queryBuilder.append("*:*");
        }
        return queryBuilder.toString();
    }

    public static String dismax(String query, Map<SolrDBMeta, Integer> qf) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("{!dismax qf='");
        qf.forEach((field, weight) -> {
            queryBuilder.append(field.fieldName());
            if (weight != null) {
                queryBuilder.append("^");
                queryBuilder.append(weight);
            }
            queryBuilder.append(" ");
        });
        if (queryBuilder.length() != 0) {
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        }
        queryBuilder.append("'}");
        queryBuilder.append(query);

        return queryBuilder.toString();
    }

    /**
     * フリーワードのスプリットした文字列を返す。
     * @param freewordSet フリーワードセット文字列
     * @return フリーワードのスプリットした配列
     */
    public static String[] splitFreeWords(String freewordSet) {
        if (DfStringUtil.is_Null_or_TrimmedEmpty(freewordSet)) {
            throw new IllegalArgumentException();
        }
        String strFreewords = normalizeFreeword(freewordSet);
        return strFreewords.split(" ");
    }

    /**
     * フリーワードの正規化する。
     * @param freewordSet フリーワードセット文字列
     * @return 正規化したワードセット文字列
     */
    public static String normalizeFreeword(String freewordSet) {
        if (freewordSet == null) {
            throw new IllegalArgumentException();
        }
        return freewordSet.replaceAll("　", " ").replaceAll(" +", " ").replaceAll("^ +", "").replaceAll(" +$", "").trim();
    }

    /**
     * 論理演算子してのEqual検索用
     * @param solrFieldName solr field name
     * @param query query
     * @return query
     */
    public static String queryBuilderForEqual(String solrFieldName, String query) {
        StringBuilder queryBuilder = new StringBuilder();
        if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
            queryBuilder.append(solrFieldName);
            queryBuilder.append(":");
            queryBuilder.append(query);
        }
        return queryBuilder.toString();
    }

    public static String queryBuilderForPrefixSearch(String solrFieldName, String query) {
        if (solrFieldName == null) {
            return "";
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(solrFieldName);
        queryBuilder.append(":");
        queryBuilder.append(query).append("*");

        return queryBuilder.toString();
    }

    public static String queryBuilderForSuffixSearch(String solrFieldName, String query) {
        if (solrFieldName == null) {
            return "";
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(solrFieldName);
        queryBuilder.append(":");
        queryBuilder.append("*").append(query);

        return queryBuilder.toString();
    }

    public static String queryBuilderForContainsSearch(String solrFieldName, String query) {
        if (solrFieldName == null) {
            return "";
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(solrFieldName);
        queryBuilder.append(":");
        queryBuilder.append("*").append(query).append("*");

        return queryBuilder.toString();
    }

    /**
     * フィールドに値が存在するかどうか判定するクエリーを作成します
     * @param solrFieldName solr field name
     * @return query
     */
    public static String queryBuilderForExists(String solrFieldName) {
        if (solrFieldName == null) {
            return "";
        }
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append(solrFieldName);
        queryBuilder.append(":");
        queryBuilder.append("*");

        return queryBuilder.toString();
    }

    /**
     * 範囲検索クエリ構築
     *
     * @param solrFieldName solr field name
     * @param from from
     * @param to to
     * @return query
     */
    public static String queryBuilderForRangeSearch(String solrFieldName, Date from, Date to) {
        String toStr = null;
        String fromStr = null;
        if (from == null) {
            fromStr = "*";
        } else {
            fromStr = formatYMDHmsForSolrByJST(from);
        }

        if (to == null) {
            toStr = "*";
        } else {
            toStr = formatYMDHmsForSolrByJST(to);
        }
        return queryBuilderForRangeSearch(solrFieldName, fromStr, toStr);
    }

    public static String queryBuilderForRangeSearch(String solrFieldName, LocalDate from, LocalDate to) {
        Date fromDate = from == null ? null : Date.from(ZonedDateTime.of(from, LocalTime.MIDNIGHT, ZoneId.systemDefault()).toInstant());
        Date toDate = to == null ? null : Date.from(ZonedDateTime.of(to, LocalTime.MIDNIGHT, ZoneId.systemDefault()).toInstant());
        return queryBuilderForRangeSearch(solrFieldName, fromDate, toDate);
    }

    public static String queryBuilderForRangeSearch(String solrFieldName, LocalDateTime from, LocalDateTime to) {
        Date fromDate = from == null ? null : Date.from(ZonedDateTime.of(from, ZoneId.systemDefault()).toInstant());
        Date toDate = to == null ? null : Date.from(ZonedDateTime.of(to, ZoneId.systemDefault()).toInstant());
        return queryBuilderForRangeSearch(solrFieldName, fromDate, toDate);
    }

    /**
     * 範囲検索クエリ構築
     * @param solrFieldName solr field name
     * @param from from
     * @param to to
     * @return query
     */
    public static String queryBuilderForRangeSearch(String solrFieldName, Number from, Number to) {
        String toStr = null;
        String fromStr = null;
        if (from == null) {
            fromStr = "*";
        } else {
            fromStr = from.toString();
        }

        if (to == null) {
            toStr = "*";
        } else {
            toStr = to.toString();
        }
        return queryBuilderForRangeSearch(solrFieldName, fromStr, toStr);
    }

    /**
     * 範囲検索クエリ構築
     *
     * @param solrFieldName solr field name
     * @param from from
     * @param to to
     * @return query
     */
    public static String queryBuilderForRangeSearch(String solrFieldName, String from, String to) {
        if (DfStringUtil.is_Null_or_Empty(from)) {
            from = "*";
        }
        if (DfStringUtil.is_Null_or_Empty(to)) {
            to = "*";
        }
        return solrFieldName + ":[" + from + " TO " + to + "]";
    }

    /**
     * セット範囲検索
     * @param solrFieldName solr field name
     * @param cd
     * @param from from
     * @param to to
     * @param paddingLength
     * @param paddingStr
     * @return query
     */
    public static String queryBuilderForSetRangeSearch(String solrFieldName, String cd, String from, String to, int paddingLength,
            String paddingStr) {
        if (DfStringUtil.is_NotNull_and_NotEmpty(cd)) {
            if (DfStringUtil.is_Null_or_Empty(from)) {
                from = SET_RANGE_SEARCH_LEFT_WILDCARD;
            }
            if (DfStringUtil.is_Null_or_Empty(to)) {
                to = SET_RANGE_SEARCH_RIGHT_WILDCARD;
            }

            if (!from.contains("*")) {
                from = leftPad(from, paddingLength, paddingStr);
            }

            if (!to.contains("?")) {
                to = leftPad(to, paddingLength, paddingStr);
            }
            return solrFieldName + ":[" + cd + VALUE_SEPARATOR + from + " TO " + cd + VALUE_SEPARATOR + to + "]";
        }
        return "";
    }

    /**
     * セット範囲検索 InScope
     * @param solrFieldName solr field name
     * @param beanList
     * @param paddingLength
     * @param paddingStr
     * @return query
     */
    public static String queryBuilderForSetRangeSearchInScope(String solrFieldName, Collection<SolrSetRangeSearchBean> beanList,
            int paddingLength, String paddingStr) {
        StringBuilder queryBuilder = new StringBuilder();
        if (beanList != null) {
            for (SolrSetRangeSearchBean bean : beanList) {
                if (queryBuilder.length() > 0) {
                    queryBuilder.append(" ");
                    queryBuilder.append(SolrQueryLogicalOperator.OR.name());
                    queryBuilder.append(" ");
                }
                queryBuilder.append(queryBuilderForSetRangeSearch(solrFieldName, bean.getCd(), bean.getFrom(), bean.getTo(), paddingLength,
                        paddingStr));
            }

            if (queryBuilder.length() > 0) {
                queryBuilder.insert(0, "(");
                queryBuilder.append(")");
            }
        }
        return queryBuilder.toString();

    }

    public static String queryBuilderForSetRangeSearchAndScope(String solrFieldName, Collection<SolrSetRangeSearchBean> beanList,
            int paddingLength, String paddingStr) {
        StringBuilder queryBuilder = new StringBuilder();
        if (beanList != null) {
            for (SolrSetRangeSearchBean bean : beanList) {
                if (queryBuilder.length() > 0) {
                    queryBuilder.append(" ");
                    queryBuilder.append(SolrQueryLogicalOperator.AND.name());
                    queryBuilder.append(" ");
                }
                queryBuilder.append(queryBuilderForSetRangeSearch(solrFieldName, bean.getCd(), bean.getFrom(), bean.getTo(), paddingLength,
                        paddingStr));
            }

            if (queryBuilder.length() > 0) {
                queryBuilder.insert(0, "(");
                queryBuilder.append(")");
            }
        }
        return queryBuilder.toString();

    }

    /**
     * フリーワード検索クエリ構築
     * ダブルコーテーションによるフレーズ検索対応
     * @param solrFieldName solr field name
     * @param queryStrs
     * @param operator operator
     * @return query
     */
    public static String queryBuilderForFreewordSearch(String solrFieldName, String queryStrs, SolrQueryLogicalOperator operator) {
        if (DfStringUtil.is_NotNull_and_NotTrimmedEmpty(queryStrs)) {
            List<String> queryList = createSearchWordList(queryStrs);
            return queryBuilderForSearchWordList(solrFieldName, queryList, operator);
        }
        return "";
    }

    public static String queryBuilderForFreewordSearchSupportIgnore(String solrFieldName, String queryStrs,
            SolrQueryLogicalOperator operator) {
        if (DfStringUtil.is_NotNull_and_NotTrimmedEmpty(queryStrs)) {
            List<String> queryList = createSearchWordList(queryStrs);
            if (operator == SolrQueryLogicalOperator.AND) {
                List<String> plusQuerys = new ArrayList<String>();
                List<String> minusQuerys = new ArrayList<String>();
                for (String query : queryList) {
                    if (query.startsWith("\"-") && query.endsWith("\"") && query.length() >= 4) {
                        minusQuerys.add("\"" + query.substring(2, query.length() - 1) + "\"");
                    } else {
                        plusQuerys.add(query);
                    }
                }

                if (minusQuerys.size() > 0 && plusQuerys.size() > 0) {
                    // プラス検索とマイナス検索があった場合、NOT検索を発動させる
                    return queryBuilderForSearchWordListAndNot(solrFieldName, plusQuerys, minusQuerys);
                }
            }

            return queryBuilderForSearchWordList(solrFieldName, queryList, operator);
        }
        return "";
    }

    /**
     * 検索ワードリストクエリ構築
     * @param solrFieldName solr field name
     * @param queryList query list
     * @param operator operator
     * @return query
     */
    public static String queryBuilderForSearchList(String solrFieldName, Collection<? extends Number> queryList,
            SolrQueryLogicalOperator operator) {
        List<String> queryStrList = new ArrayList<String>();
        if (isNotEmptyStrict(queryList)) {
            for (Number num : queryList) {
                queryStrList.add(num.toString());
            }
        }
        return queryBuilderForSearchWordList(solrFieldName, queryStrList, operator);
    }

    /**
     * 検索ワードリストクエリ構築
     * 例 NOT： candidate_id: Not (1000 OR 10002 OR 10004)
     * 例 OR： candidate_id: (1000 OR 10002 OR 10004)
     * 例 AND： candidate_id: (1000 AND 10002 AND 10004)
     * @param solrFieldName solr field name
     * @param queryList query list
     * @param operator operator
     * @return query
     */
    public static String queryBuilderForSearchWordList(String solrFieldName, Collection<String> queryList,
            SolrQueryLogicalOperator operator) {
        StringBuilder queryBuilder = new StringBuilder();
        SolrQueryLogicalOperator partOperator = operator;
        if (isNotEmptyStrict(queryList)) {
            if (SolrQueryLogicalOperator.NOT.equals(operator)) {
                queryBuilder.append(SolrQueryLogicalOperator.NOT.name());
                queryBuilder.append(" ");
                partOperator = SolrQueryLogicalOperator.OR;
            }
            queryBuilder.append(solrFieldName);
            queryBuilder.append(": ");
            queryBuilder.append(queryBuilderForSearchWordListPart(queryList, partOperator));
        }
        return queryBuilder.toString();
    }

    public static String queryBuilderForSearchWordListAndNot(String solrFieldName, Collection<String> andQueryList,
            Collection<String> notQueryList) {
        StringBuilder queryBuilder = new StringBuilder();
        if (isNotEmptyStrict(andQueryList) && isNotEmptyStrict(notQueryList)) {
            queryBuilder.append(solrFieldName);
            queryBuilder.append(": ");
            queryBuilder.append(queryBuilderForSearchWordListPartRaw(andQueryList, SolrQueryLogicalOperator.AND));
            queryBuilder.append(" NOT");
            queryBuilder.append(queryBuilderForSearchWordListPartRaw(notQueryList, SolrQueryLogicalOperator.NOT));
        }
        return queryBuilder.toString();
    }

    /**
     * 検索Word Listクエリの構築
     * 例：(1000 OR 10002 OR 10004)
     * @param queryList query list
     * @param operator operator
     * @return query
     */
    private static String queryBuilderForSearchWordListPart(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("(");
        boolean isNotfirst = false;
        for (String query : queryList) {
            if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
                if (isNotfirst) {
                    queryBuilder.append(operator.name());
                }
                queryBuilder.append(" ");
                queryBuilder.append(query);
                queryBuilder.append(" ");
                isNotfirst = true;
            }
        }
        queryBuilder.append(")");
        return queryBuilder.toString();
    }

    private static String queryBuilderForSearchWordListPartRaw(Collection<String> queryList, SolrQueryLogicalOperator operator) {
        StringBuilder queryBuilder = new StringBuilder();
        boolean isNotfirst = false;
        for (String query : queryList) {
            if (DfStringUtil.is_NotNull_and_NotEmpty(query)) {
                if (isNotfirst) {
                    queryBuilder.append(operator.name());
                }
                queryBuilder.append(" ");
                queryBuilder.append(query);
                queryBuilder.append(" ");
                isNotfirst = true;
            }
        }
        return queryBuilder.toString();
    }

    /**
     * 検索文字列作成
     * 検索窓に入れたワードを検索フレーズ、単語単位のリストにします
     * フレーズ検索（""ダブルコーテーション）にも対応しています
     * "fund manager" ゴールドマンサックス ⇒ "fund manager", "ゴールドマンサックス"
     * @param str 文字列
     * @return query
     */
    public static List<String> createSearchWordList(final String str) {
        if (DfStringUtil.is_Null_or_TrimmedEmpty(str)) {
            throw new IllegalArgumentException("空白文字列、又はnullが入力されました");
        }
        String normalizedStr = normalizeFreeword(str);
        List<String> excludedPhraseList = getExcludedPhraseList(normalizedStr);
        List<String> phraseList = getPhraseList(normalizedStr);
        List<String> arrayList = new ArrayList<String>();
        if (isNotEmptyStrict(excludedPhraseList)) {
            arrayList.addAll(excludedPhraseList);
        }
        if (isNotEmptyStrict(phraseList)) {
            arrayList.addAll(phraseList);
        }
        return arrayList;
    }

    /**
     * 連結した値を持つフィールドの検索値を生成する<br />
     * <pre>
     * 例：求職者が、企業1をブロックしいているラベル
     *    BLK_CMP_1
     * </pre>
     * @param args arguments
     * @return query
     */
    public static String createCombinationQueryValue(Object... args) {
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("nullもしくは空の配列が入力されました");
        }
        return Arrays.stream(args).map(arg -> arg.toString()).collect(Collectors.joining(VALUE_SEPARATOR));
    }

    /**
     * 2値を連結し、後方値をlpadした値を持つフィールドの検索値を生成する<br />
     * <pre>
     * 例：求職者クラスの下限年収
     *  　　RG_01000
     * </pre>
     * @param value1 前方値
     * @param value2 後方値
     * @param lpadLength lpadの桁数
     * @param lpadChar lpad実行で挿入する文字列
     * @return query
     */
    public static String createDoubleQueryValue(String value1, String value2, int lpadLength, Character lpadChar) {
        if (value1 == null || value2 == null) {
            throw new IllegalArgumentException("valueにnullが入力されました");
        }
        Character c = lpadChar == null ? '0' : lpadChar;

        return value1 + VALUE_SEPARATOR + leftPad(value2, lpadLength, c.toString());
    }

    /**
     * フレーズ以外の部分をリスト化して取得します
     * @param str 文字列
     * @return query
     */
    public static List<String> getExcludedPhraseList(String str) {
        Matcher phrasePartMatcher = BOOLEAN_PHRASE_PART.matcher(str);
        String exceptPhraseWord = phrasePartMatcher.replaceAll("").trim();
        exceptPhraseWord = exceptPhraseWord.replaceAll("\"", "");
        exceptPhraseWord = normalizeFreeword(exceptPhraseWord);
        String[] splitedArray = exceptPhraseWord.split(" ");
        List<String> excludedPhraseList = new ArrayList<String>();
        for (int i = 0; i < splitedArray.length; i++) {
            excludedPhraseList.add("\"" + splitedArray[i] + "\"");
        }
        return excludedPhraseList;
    }

    /**
     * フレーズ部分をリスト化して取得します
     * @param str 文字列
     * @return query
     */
    public static List<String> getPhraseList(String str) {
        Matcher phrasePartMatcher = BOOLEAN_PHRASE_PART.matcher(str);
        List<String> phraseList = new ArrayList<String>();
        while (phrasePartMatcher.find()) {
            String phraseStr = phrasePartMatcher.group();
            phraseList.add("\"" + phraseStr.replaceAll("\"", "").trim() + "\"");
        }
        return phraseList;
    }

    /**
     * 厳しいListのチェック
     * @param collection collection
     * @return 空でない場合、{@code true}
     */
    private static <E> boolean isNotEmptyStrict(Collection<E> collection) {
        return !isEmptyStrict(collection);
    }

    /**
     * 厳しいListのチェック
     * @param collection collection
     * @return 空の場合、{@code true}
     */
    private static <E> boolean isEmptyStrict(Collection<E> collection) {
        if (collection == null || collection.size() == 0) {
            return true;
        }

        // String型のListは内容の空チェックを行い、その他の場合はnullチェックを行います。
        if (collection instanceof List<?>) {
            if (((List<?>) collection).get(0) instanceof String) {
                for (E s : collection) {
                    if (DfStringUtil.is_NotNull_and_NotEmpty(String.class.cast(s))) {
                        return false;
                    }
                }
                return true;
            } else {
                for (E s : collection) {
                    if (s != null) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Solr向けDate型の検索文字列フォーマットに変換します。DateがJSTの場合に使います
     * @param date 日付
     * @return　Solr向けのUTC形式でDate型の検索文字列フォーマットにした日時文字列
     */
    public static String formatYMDHmsForSolrByJST(Date date) {
        // XXX p1us2er0 -9ベタでいいか確認。 (2015/05/26)
        return formatYMDHmsForSolr(date, -9);
    }

    /**
     * Solr向けのUTC形式でDate型の検索文字列フォーマットに変換します
     * @param date 変換したい文字列
     * @param timeZoneDiff タイムゾーンごとにUTCにする為の時差(例：JSTなら-9でUTCになるので-9をいれる)
     * @return　Solr向けのUTC形式でDate型の検索文字列フォーマットにした日時文字列
     */
    public static String formatYMDHmsForSolr(Date date, int timeZoneDiff) {
        return DATE_TIME_FORMATTER.format(new HandyDate(date).addHour(timeZoneDiff).getLocalDateTime());
    }

    public static void assertNullQuery(String fieldName, String query) {
        if (fieldName == null) {
            String msg = "fieldName should not be null: fieldName=null query=" + query;
            throw new IllegalArgumentException(msg);
        }
        if (query != null) {
            String msg = "Query for this field is already registered: fieldName:query=" + query;
            throw new IllegalArgumentException(msg);
        }
    }

    public static void assertNotNullQuery(String fieldName, String query) {
        if (fieldName == null) {
            String msg = "fieldName should not be null: fieldName=null query=" + query;
            throw new IllegalArgumentException(msg);
        }
        if (query == null) {
            String msg = "The query should not be null: fieldName=" + fieldName;
            throw new IllegalArgumentException(msg);
        }
    }

    // XXX p1us2er0 org.apache.commons.lang3.StringUtilsのコピー。時間があるときに最適化。 (2016/09/25)
    /**
     * <p>Left pad a String with a specified String.</p>
     *
     * <p>Pad to a size of {@code size}.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)      = null
     * StringUtils.leftPad("", 3, "z")      = "zzz"
     * StringUtils.leftPad("bat", 3, "yz")  = "bat"
     * StringUtils.leftPad("bat", 5, "yz")  = "yzbat"
     * StringUtils.leftPad("bat", 8, "yz")  = "yzyzybat"
     * StringUtils.leftPad("bat", 1, "yz")  = "bat"
     * StringUtils.leftPad("bat", -1, "yz") = "bat"
     * StringUtils.leftPad("bat", 5, null)  = "  bat"
     * StringUtils.leftPad("bat", 5, "")    = "  bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padStr  the String to pad with, null or empty treated as single space
     * @return left padded String or original String if no padding is necessary,
     *  {@code null} if null String input
     */
    public static String leftPad(final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (DfStringUtil.is_Null_or_Empty(padStr)) {
            padStr = " ";
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            final char[] padding = new char[pads];
            final char[] padChars = padStr.toCharArray();
            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }

    /**
     * <p>Left pad a String with a specified character.</p>
     *
     * <p>Pad to a size of {@code size}.</p>
     *
     * <pre>
     * StringUtils.leftPad(null, *, *)     = null
     * StringUtils.leftPad("", 3, 'z')     = "zzz"
     * StringUtils.leftPad("bat", 3, 'z')  = "bat"
     * StringUtils.leftPad("bat", 5, 'z')  = "zzbat"
     * StringUtils.leftPad("bat", 1, 'z')  = "bat"
     * StringUtils.leftPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padChar  the character to pad with
     * @return left padded String or original String if no padding is necessary,
     *  {@code null} if null String input
     * @since 2.0
     */
    public static String leftPad(final String str, final int size, final char padChar) {
        if (str == null) {
            return null;
        }
        final int pads = size - str.length();
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return repeat(padChar, pads).concat(str);
    }

    public static String repeat(final char ch, final int repeat) {
        final char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--) {
            buf[i] = ch;
        }
        return new String(buf);
    }
}
