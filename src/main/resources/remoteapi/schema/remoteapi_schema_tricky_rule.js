// _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
// RemoteApiGen your rule settings as ECMAScript5 (related to RemoteApiRule.js in freegen)
// _/_/_/_/_/_/_/_/_/_/

// =======================================================================================
//                                                                                   Base
//                                                                                  ======
// 自動生成対象を調整する(RequestもResponseのないAPIの自動生成) @until 2019/08/31
// @since 2019/09/01 RemoteApiRule.jsですべてtrueになっています。
remoteApiRule.target = function(api) {
    // 自動生成対象から除外したいURLがあれば、他のremoteApiRule.targetのoverrideの指定方法を参考にしてください。
    return true;
};

// 自動生成対象から除外する(URLで指定)
remoteApiRule.target = function(api) {
    if (!baseRule.target(api)) {
        return false;
    }
    var targetUrls = [
        '/xxx/yyy/zzz'
    ];
    for (var targetUrlIndex in targetUrls) {
        if (targetUrls[targetUrlIndex].indexOf(api.url) !== -1) {
            return true;
        }
    }
    return false;
};

// 自動生成対象から除外する(Content-Typeで指定)

// 自動生成時のパッケージからURLの一部(バージョン番号など)を除去
remoteApiRule.subPackage = function(api) {
    return baseRule.subPackage(api).replace(/\.v1/g, '');
};

// =======================================================================================
//                                                                                Behavior
//                                                                                ========
// Bhvクラスのサブパッケージを分けずにフラットにする。
remoteApiRule.behaviorSubPackage = function(api) {
    return '';
};

// =======================================================================================
//                                                                            Param/Return
//                                                                            ============
// 自動生成するParam/Returnクラスの親Definitionクラスを生成します。
remoteApiRule.beanExtendsDefinitionGeneration = true;

// 自動生成するParamクラスにimplementsするインターフェースを指定する。
remoteApiRule.paramImplementsClasses = function(api, properties) {
    return 'xxx.yyy.zzz.AbcParam';
};

// 自動生成するReturnクラスにimplementsするインターフェースを指定する。
remoteApiRule.returnImplementsClasses = function(api, properties) {
    return 'xxx.yyy.zzz.AbcReturn';
};

// 自動生成するParam/Returnクラスのネストクラスの名前を調整する。
remoteApiRule.nestClassName = function(api, className) {
    return className.replace(/Xxx$/, '') + 'Yyy';
    // 以下はデフォルト設定。
    // lastafluteの推奨suffixのPart、Spring MVCの推奨suffixのModel、Javaの一般的なsuffixのBeanを除去して、Partをsuffixにつける。
    // ※Resultの除去は互換性のために残しています。
    // return className.replace(/(Part|Result|Model|Bean)$/, '') + 'Part';
};

// =======================================================================================
//                                                                                  Option
//                                                                                  ======
// 自動生成ファイルのimprot宣言の順番を調整する。
remoteApiRule.importOrderList = function() {
    return ['java', 'javax', 'junit', 'org', 'com', 'net', 'ognl', 'mockit', 'jp'];
},

// リクエストやレスポンスの各フィールド名を調整する。
// デフォルトは、小文字スネークケースからキャメルケースに変換して自動生成します。
// 変換しないようにする場合は、this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKEをnullにしてください。
remoteApiRule.fieldNamingMapping = function() {
    return {
        'path': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE,
        'query': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE,
        'formData': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE,
        'json': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE,
        'xml': this.FIELD_NAMING.CAMEL_TO_LOWER_SNAKE
    };
},

// -----------------------------------------------------
//                                          Type Mapping
//                                          ------------
// swagger.jsonの型とJavaの型を調整する。
remoteApiRule.typeMap = function() {
    var typeMap = baseRule.typeMap();
    // 配列(リスト)のマッピングを、java.util.ListからEclipse CollectionsのImmutableListに変更。
    typeMap['array'] = 'org.eclipse.collections.api.list.ImmutableList';
    // 日時のマッピングを、java.time.LocalDateTimeからjava.time.ZonedDateTimeに変更。    
    typeMap['date-time'] = 'java.time.ZonedDateTime';
    return typeMap;
};

// -----------------------------------------------------
//                                              clean up
//                                              --------
// 自動生成後のファイルのクリーンアップ(削除)対象を調整する。
// @since 2019/11/31 ファイルの中身に「 @author FreeGen」が含まれるファイルがクリーンアップ(削除)対象になります。
remoteApiRule.deleteTarget = function(request, file) {
    // 標準の動きで問題がある場合は、条件を足してください。
	return baseRule.deleteTarget(request, file) && xxx;
};
