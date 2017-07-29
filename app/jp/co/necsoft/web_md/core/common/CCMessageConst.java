// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 機能名称 : 共通メッセージ定数クラス 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 2014.02.04 H.Okuhara 新規作成 ===================================================================
 */
package jp.co.necsoft.web_md.core.common;

/**
 * 共通メッセージ定数クラス
 *
 */
public class CCMessageConst {
    /**月2回精算のテナントが存在しません。*/
    public static final String MSG_KEY_TENANT_15DATE_NOT_EXISTED = "Y0011";
    /**月1回精算のテナントが存在しません。*/
    public static final String MSG_KEY_TENANT_30DATE_NOT_EXISTED = "Y0012";    
    /**月2回精算と月1回精算のテナントが存在しません。*/
    public static final String MSG_KEY_TENANT_15_30DATE_NOT_EXISTED = "Y0013";
    /**中間精算を行うテナントがありません。*/
    public static final String MSG_KEY_CHUKAN_SEISAN_NOT_EXISTED = "Y0004";
    /**月末精算を行うテナントがありません。*/
    public static final String MSG_KEY_GETSUMATSU_SEISAN_NOT_EXISTED = "Y0005";
    /**精算処理を行う日～次回精算時に設定される支払日で入力してください。*/
    public static final String MSG_KEY_PAY_DATE_INPUT_FAILED = "Y0006";
    /**土曜日または日曜日の以外の日付を入力してください。*/
    public static final String MSG_KEY_FURIKOMI_DATE_INPUT_FAILED = "Y0007";   
    /**既に本精算済み。*/
    public static final String MSG_KEY_HONSEISAN_ALREADY = "Y0008";   
    /**前回分の本精算が終了していません。*/
    public static final String MSG_KEY_PRE_HONSEISAN_NOT_END = "Y0009";   
    
    
    /**１明細も入力されていません。*/
    public static final String MSG_KEY_ONE_SPECIFICATION_DOES_NOT_ENTER = "U0013";
    /**棚卸対象月ではないため修正できません。*/
    public static final String MSG_KEY_NOT_MODIFY_MONTH = "Z0013";
    /**確定後のデータのため更新できません。*/
    public static final String MSG_KEY_NOT_UPDATE = "Z0017";
    /**移動平均原価在庫のため修正できません。*/
    public static final String MSG_KEY_NOT_MODIFY = "Z0015";
    /**<%S%>に登録されていません。*/
    public static final String MSG_KEY_NOT_REGISTERED_FOR = "C0261";
    /**入力した銀行コード及び支店コードを持つレコードが銀行マスタに登録されていません。*/
    public static final String MSG_KEY_NOT_REGISTERED_FOR_BANK_CD = "C9030";
    /**指定されたキーは未登録です。*/
    public static final String MSG_KEY_NOT_EXIST_REGISTERED = "C0004";
    /**コード区分は半角3桁で入力して下さい。*/
    public static final String MSG_KEY_MAX_LENGTH_CD_KBN = "T2121";
    /**コードは半角16桁で入力して下さい。*/
    public static final String MSG_KEY_MAX_LENGTH_CD = "T2122";
    /**データが正しく編集されました。*/
    public static final String MSG_KEY_CHANGE_SUCCESS = "Z0036";
    /**データが正しく登録されました。*/
    public static final String MSG_KEY_SUCCESS_SAVE_DATA = "C1001";
    /**データが正しく更新されました。*/
    public static final String MSG_KEY_SUCCESS_UPDATE_DATA = "C1002";
    /**データが正しく削除されました。*/
    public static final String MSG_KEY_SUCCESS_DELETE_DATA = "C1003";
    /**対象キーのレコードが存在しません。*/
    public static final String MSG_KEY_NO_DATA = "C1004";
    /**対象データがありません。*/
    public static final String MSG_KEY_NO_FOUND_DATA = "C0105";
    /**日付に誤りがあります。*/
    public static final String MSG_KEY_DATE_ERROR = "C0011";
    /**この項目は省略できません。*/
    public static final String MSG_KEY_NOT_KIP_ITEM = "C0012";
    /**入力された値に誤りがあります。各項目のエラーメッセージを確認してください。*/
    public static final String MSG_KEY_VALIDATION_ERROR = "C1005";
    /**入力した仕向先銀行コード及び仕向先支店コードを持つレコードが銀行マスタに登録されていません。*/
    public static final String MSG_KEY_VALIDATION_GRID_ITEM_NOT_EXIST = "C9029";
    /**明細部を編集し[保存]ボタンで確定するか、[削除]ボタンでデータを削除してください。*/
    public static final String MSG_KEY_EDIT_DETAIL_EXISTING_DATA = "C1006";
    /**新規に明細部を入力し[保存]ボタンで確定してください。*/
    public static final String MSG_KEY_EDIT_DETAIL_NEW_DATA = "C1007";
    /**サーバー処理でシステムエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_SYSTEM_ERROR = "C1008";
    /**マスタに登録されていません。*/
    public static final String MSG_KEY_NOT_EXISTS_IN_MASTER = "C2001";
    /**運用日以前の日付です。*/
    public static final String MSG_KEY_DATE_IN_PAST = "C2002";
    /**対象キーのレコードが存在しません。新規登録しますか？*/
    public static final String MSG_KEY_CONFIRM_NEW_REGISTRATION = "C3001";
    /**データを登録します。よろしいですか？*/
    public static final String MSG_KEY_CONFIRM_SAVE_DATA = "C3002";
    /**データを更新します。よろしいですか？*/
    public static final String MSG_KEY_CONFIRM_UPDATE_DATA = "C3003";
    /**データを削除します。よろしいですか？*/
    public static final String MSG_KEY_CONFIRM_DELETE_DATA = "C3004";
    /**入力中のデータをクリアします。よろしいですか？*/
    public static final String MSG_KEY_CONFIRM_CLEAR_DATA = "C3005";
    /**未来情報が存在します。必要に応じてそちらもメンテしてください。*/
    public static final String MSG_KEY_EXISTS_FUTURE_DATA = "C3006";
    /**発効日が1ヶ月以上未来の日付です。よろしいですか？*/
    public static final String MSG_KEY_ONE_MONTH_FUTURE = "C3007";
    /**稼動区分:<%S%>,発効日: <%S%> の未来情報が存在します。*/
    public static final String MSG_KEY_STATUS_EXISTS_FUTURE_DATA = "C3008";
    /**入力された数値が不正です。*/
    public static final String MSG_KEY_NOT_NUMBER = "C8030";
    /**対象キーはすでに登録されています。*/
    public static final String MSG_KEY_INPUT_NOT_EDIT = "C0252";
    /**自動FAXする為、FAX番号を入力して下さい。*/
    public static final String MSG_KEY_INPUT_FAX_REQURIED = "M0010";
    /**この項目は入力できません。*/
    public static final String MSG_KEY_INPUT_NOT_ITEM = "C0015";
    /**運用日以前の日付を入力して下さい*/
    public static final String MSG_KEY_INPUT_DATE_BEFORE_ACTIVE = "Z0035";
    /**当月かつ今日以前の日付を入力して下さい。                                        */
    public static final String MSG_KEY_INPUT_TODAY_DATE_AND_PREVIOUS_MONTH = "Z0002";
    /**今日以前の日付を入力して下さい。*/
    public static final String MSG_KEY_INPUT_BEFORE_TODAY = "Z0030";
    /**対象キーのレコードが存在しません。*/
    public static final String MSG_KEY_NOT_EXISTS = "C0253";
    /**入力データが不正です。<%S%>*/
    public static final String MSG_KEY_INPUT_DATA_INVALID = "C0254";
    /**入力データが不正です。仕向先銀行コードと仕向先支店コードは両方入力して下さい。*/
    public static final String MSG_KEY_INPUT_DATA_INVALID_IS_NULL = "C9041";
    /**<%S%>は<%S%>で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE = "C0257";
    /**半角数字1桁（1:EOS(FIP) 4:自動FAX 5:伝票出力 9:EOS対象外）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_1459 = "C9022";
    /**半角1桁（1:休止 9:通常）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_19 = "C9023";
    /**半角数字1桁（1:通常 3:内部取引先）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_13 = "C9024";
    /**半角1桁（1:取引先負担 2:自社負担）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_12 = "C9034";
    /**半角1桁（1:設定せず 2:設定）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_12_2 = "C9035";
    /**半角数字1桁（1:普通 2:当座 4:貯蓄 9:その他）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_1249 = "C9025";
    /**半角数字2桁（15:１５日毎締め 30:３０日末締め）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_1530 = "C9026";
    /**半角数字2桁（10:１０日後 15:１５日後 30:翌月末）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_101530 = "C9033";
    /**半角数字1桁（1:ｵﾝﾗｲﾝ振込 2:振込(手書) 3:本部現金 4:小口精算 5:手形）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_12345 = "C9027";
    /**半角数字1桁（1:× 2:月中 3:月末）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_123 = "C9028";
    /**半角数字1桁（1:取込 9:取り込まない）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_19_2 = "C9031";
    /**半角1桁（1:ＴＣ 2:ＤＣ 9:対象外）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_129 = "C9032";
    /**半角数字1桁（1:ASNオンライン対象 2:ASNオンライン対象外）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_ASN_FLG = "C9038";
    /**半角数字1桁（1:印刷対象 9:対象外）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_DIRECT_KBN = "C9039";
    /**半角数字1桁（1:送信対象 9:対象外）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_FEE_KBN = "C9040";
    /**<%S%>を入力して下さい。*/
    public static final String MSG_KEY_INPUT_REQUIRED = "C0258";
    /**締区分が15:１５日毎締め の場合には支払区分に半角数字2桁（10:１０日後 15:１５日後）を入力して下さい。*/
    public static final String MSG_KEY_INPUT_REQUIRED_15 = "C9036";
    /**締区分が30:３０日末締め の場合には支払区分に半角数字2桁（15:１５日後 30:翌月末）を入力して下さい。*/
    public static final String MSG_KEY_INPUT_REQUIRED_30 = "C9037";
    /**<%S%>を再入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_DATE = "C0259";
    /**<%S%>が登録されていません。*/
    public static final String MSG_KEY_NOT_REGISTERED = "C0260";
    /**納品日の時点で、ＪＡＮコードが登録されていません。*/
    public static final String MSG_KEY_NOT_REGISTERED_NOHINDAY_JANCD = "T2120";
    /**企画マスタの整合性エラーの為、ＤＢ処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_KIKAKU_DATA_INCONSISTENT = "T2125";
    /**複写元のデータ"が登録されていません。*/
    public static final String MSG_KEY_COPY_SOURCE_NOT_EXIST = "T2126";
    /**既に発注日を過ぎているため、変更できません。*/
    public static final String MSG_KEY_PAST_ORDER_DATE = "T2127";
    /**既に販売期間（開始日）を過ぎているため、変更できません。*/
    public static final String MSG_KEY_PAST_SALE_DATE = "T2128";
    /**既に発注可能日（開始）を過ぎているため、変更できません。*/
    public static final String MSG_KEY_PAST_START_DATE = "T2129";
    /**納品日が範囲外です。*/
    public static final String MSG_KEY_DATE_OUT_OF_RANGE = "T0021";
    /**運用日以前の日付です。*/
    public static final String MSG_KEY_BEFORE_UNYOBI_DAY = "T0022";
    /**既に<%S%>件登録されている為、これ以上登録できません。*/
    public static final String MSG_KEY_GREATER_OR_EQUAL_999 = "M0001";
    /**入力した店舗コードに該当する<%S%>が登録されていません。*/
    public static final String MSG_KEY_DID_NOT_SAVE = "M0002";
    /**<%S%>を入力し、F12(実行)キーを押してください。*/
    public static final String MSG_KEY_GREATER_THAN_30 = "C0201";
    /**<%S%>を確認し、F12(実行)キーを押してください。*/
    public static final String MSG_KEY_DATA_INVALID = "C0202";
    /**<%S%>処理に失敗しました。*/
    public static final String MSG_KEY_IS_NULL_OR_EMPTY = "C0251";
    /**<%S%>処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_NO_DATA_RESULT = "C0299";
    /**参照処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_NO_DATA_RESULT_REFERENCE = "C9021";
    /**対象キーはすでに登録されています。*/
    public static final String MSG_KEY_ALREADY_REGISTERED = "C0252";
    /**この項目は省略できません。*/
    public static final String MSG_KEY_DATA_IS_EMPTY = "C0012";
    /**この項目は数字入力項目です。*/
    public static final String MSG_KEY_NOT_A_NUMBER = "C0014";
    /**同一コードが既に入力されています。*/
    public static final String MSG_KEY_DATA_IS_DUPLICATE = "C0020";
    /**桁数エラー*/
    public static final String MSG_KEY_VALUE_OVER_LIMIT = "C0067";
    /**金額と消費税の合計は大きすぎます。*/
    public static final String MSG_KEY_TAX_SUM_TOO_LARGE = "K1062";
    /**<%S%>を再入力して下さい。*/
    public static final String MSG_KEY_RE_ENTER = "C0259";
    /**納品日が発注日以前の日付になっています。*/
    public static final String MSG_KEY_ORDER_DATE_FAIL = "T0061";
    /**対象データには、０または１を入力して下さい。*/
    public static final String MSG_KEY_DATA_ENTER_0_OR_1 = "T2132";
    /**取引項目データは存在しません。*/
    public static final String MSG_KEY_TORIHIKI_NOT_EXISTS = "K1003";
    /**勘定区分エラー　１：支払勘定　２：相殺勘定*/
    public static final String MSG_KEY_INPUT_KANJO_INVALID = "K1005";
    /**勘定区分は登録されていません。*/
    public static final String MSG_KEY_INPUT_KANJO_NOT_EXISTS = "K1006";
    /**科目名称は登録されていません。*/
    public static final String MSG_KEY_INPUT_KMK_NOT_EXISTS = "K1007";
    /**税区分エラー　５?８：外税　１?４：内税　９：非課税*/
    public static final String MSG_KEY_INPUT_TAXKBN_INVALID = "K1008";
    /**確定区分エラー　２：確定　３：保留*/
    public static final String MSG_KEY_INPUT_JOTAIKBN_INVALID = "K1018";
    /**税区分は登録されていません。*/
    public static final String MSG_KEY_INPUT_TAXKBN_NOT_EXISTS = "K1009";
    /**管理No.の範囲指定に誤りがあります。*/
    public static final String MSG_KEY_RANGE_SPECIFICATION_MANAGEMENT_NUMBER_ERROR = "K1010";
    /**確定区分エラー　１：登録　２：確定　３：保留*/
    public static final String MSG_KEY_KANJO_KBN_SHR_KANJYO_NOT_EXISTS = "K1013";
    /**取引項目コードの勘定区分に誤りがあります。*/
    public static final String MSG_KEY_KANJO_KBN_NOT_EXISTS = "K1014";
    /**登録すべきデータが存在しません。*/
    public static final String MSG_KEY_EMPTY_DATA_EXISTS = "K1015";
    /**取引先コードは存在しません。*/
    public static final String MSG_KEY_DATA_NOT_EXISTS = "K1016";
    /**消費税を入力した場合、この項目は省略できません。*/
    public static final String MSG_KEY_SHRKIN_NOT_EXISTS = "K1017";
    /**取引先コードの範囲指定に誤りがあります。*/
    public static final String MSG_KEY_RANGE_SUPPLIER_CODE_ERROR = "K1019";
    /**取引先マスタ　取引先区分エラー　１：通常　３：内部取引先*/
    public static final String MSG_KEY_HATTRIKBN_INVALID = "K1041";
    /**消費税が誤っています。*/
    public static final String MSG_KEY_SOTOTAXKIN_INVALID = "K1047";
    /**既に支払済みとなっています。*/
    public static final String MSG_KEY_COUNT_ERROR = "K1049";
    /**店舗または部門のどちらか一方は全店または全部門指定を行って下さい。*/
    public static final String MSG_KEY_NOT_BELONG_TO_DEPART = "Z0027";
    /**指定された店舗コードは入力できません。*/
    public static final String MSG_KEY_TEN_CD_GREATER_OR_EQUAL_DAMMY_TENPO = "Z0008";
    /**指定されたキーは未登録です。*/
    public static final String MSG_KEY_RESULT_NOT_EXISTS = "C0004";
    /**他ユーザが編集中のため、編集処理は行うことができません。*/
    public static final String MSG_KEY_SHMG_NOT_EXISTS = "C0241";
    /**対象データがありません。*/
    public static final String MSG_KEY_BUMON_NO_DATA = "C0105";
    /**昨対反映処理を行いました。内容 他店舗実績取得処理を行いました。内容を確認し、F12(保存)キーを押してください。*/
    public static final String MSG_KEY_URJP0010_DATA_INVALID = "C9094";
    /**基本情報マスタに登録されていません。*/
    public static final String MSG_KEY_NOT_REGISTER_IN_MASTER = "S1017";
    /**指定した商品コードと部門コードが一致しません。*/
    public static final String MSG_KEY_NOT_REGISTER_IN_MASTER_1 = "S1013";
    /**店別商品マスタに登録されていません。*/
    public static final String MSG_KEY_NOT_SHOP_REGISTER_IN_MASTER = "S1018";
    /**店舗未取扱いの商品が指定されました。*/
    public static final String MSG_KEY_SHOHIN_CODE_NOT_VALID_3 = "S1023";
    /**過剰納品です。*/
    public static final String MSG_KEY_PRODUCT_NON_SROTE = "S1025";
    /**入力された部門では取り扱えない商品が指定されました。*/
    public static final String MSG_KEY_PRODUCT_DEPARTMENT_INVALID = "S1038";
    /**数量が未入力です。*/
    public static final String MSG_KEY_PRODUCT_QUANTITY_EMPTY = "S1027";
    /**入力された桁数が大きすぎます。<%S%>*/
    public static final String MSG_KEY_NUMBER_TOO_LARGE = "S1036";
    /**マイナス値が入力されています。*/
    public static final String MSG_KEY_NUMBER_NEGATIVE_INVALID = "S1024";
    /**用度品を用度品以外の部門に移動することはできません。*/
    public static final String MSG_KEY_TWO_YODOKBN_NOT_VALID_1 = "S1202";
    /**商品を用度品の部門に移動することはできません。*/
    public static final String MSG_KEY_TWO_YODOKBN_NOT_VALID_2 = "S1203";
    /**入荷会社コードと出荷会社コードが異なるため、入力できません。*/
    public static final String MSG_KEY_TWO_VALUE_NOT_EQUAL = "S1215";
    /**当月以前の伝票入力は行えません。*/
    public static final String MSG_KEY_DATE_OVER = "S1099";
    /**指定された伝票は未登録です。*/
    public static final String MSG_KEY_SLIP_NOT_REGISTER = "C0018";
    /**０は指定できません。*/
    public static final String MSG_KEY_VALUE_IS_ZERO = "C0019";
    /**入力された項目に誤りがあります。*/
    public static final String MSG_KEY_ITEM_INPUT_IS_ERROR = "C0062";
    /**当日以前の日付を指定してください。*/
    public static final String MSG_KEY_DATE_BEFORE = "U0001";
    /**入力できる売仕部門ではありません。*/
    public static final String MSG_KEY_NOT_UTSUKAMATSU = "S1263";
    /**入力された部門は売仕部門ではありません。*/
    public static final String MSG_KEY_NOT_ENTERED_UTSUKAMATSU = "S1212";
    /**入力された部門は売仕部門です。*/
    public static final String MSG_KEY_ENTERED_UTSUKAMATSU = "S1213";
    /**入力された取引先は休止状態です。*/
    public static final String MSG_KEY_SUPPLIERS_IS_HIBERNATION = "S1271";
    /**指定された伝票は入力できません。*/
    public static final String MSG_KEY_CAN_NOT_ENTER_VOUCHER = "S1280";
    /**<%S%>が<%S%>にすでに登録されています。*/
    public static final String MSG_KEY_IS_ALREADY_REGISTERED = "S1256";
    /**<%S%>は入力出来ません。*/
    public static final String MSG_KEY_CAN_NOT_INPUT = "C0303";
    /**便　エラー　１～３までです。*/
    public static final String MSG_KEY_ERROR_STOOLS = "H0104";
    /**<%S%>となっています。*/
    public static final String MSG_KEY_ERROR_COMPARE = "H0123";
    /**発注日に、発注運用日から60日以降の日付が指定されています。*/
    public static final String MSG_KEY_HATUDAY_UNYOUDAY_LATER_60 = "H0207";
    /**納品日に、発注日から30日以降の日付が指定されています。*/
    public static final String MSG_KEY_HATUDAY_LATER_30 = "H0208";
    /**商品コードが未入力です。*/
    public static final String MSG_KEY_DATA_INPUT_INVALID = "S1022";
    /**商品情報の取得に失敗しました。*/
    public static final String MSG_KEY_FAIL_GET_DATA_PRODUCT = "S1075";
    /**入力原単価＞マスタ原単価*/
    public static final String MSG_KEY_PRICE_GREATER_MASTER = "S1039";
    /**定番原単価＜＞入力原単価*/
    public static final String MSG_KEY_PRICE_DIFFERENT_MASTER = "S1242";
    /**売価還元法対象外の商品のため、売価は0に設定して下さい。*/
    public static final String MSG_KEY_PRICE_SELL_ZERO = "S1243";
    /**マスタ売単価＜＞入力売単価*/
    public static final String MSG_KEY_PRICE_DIFFERENT_SALES = "S1269";
    /**０は指定できません。*/
    public static final String MSG_KEY_NOT_INPUT_ZERO = "S1270";
    /**税区分取得エラー*/
    public static final String MSG_KEY_TAX_CATEGORY_ERROR = "S1262";
    /**１伝票内で税区分が混在しています。*/
    public static final String MSG_KEY_TAX_CATEGORY_MIX_SLIP_INSIDE = "S1044";
    /**明細が入力されていません。*/
    public static final String MSG_KEY_VALUE_NO_INPUT = "C0072";
    /**原価金額合計欄と入力した原価の合計が一致していません。*/
    public static final String MSG_KEY_NOT_MATCH_VALUE_TWO_COLUMN = "S1032";
    /**売価金額合計欄と入力した売価の合計が一致していません。*/
    public static final String MSG_KEY_NOT_MATCH_VALUE_PRICE_TOTAL_AMOUNT = "S1033";
    /**他店舗の伝票更新は行えません。*/
    public static final String MSG_KEY_CANT_UPDATE_STORE = "S1058";
    /**確定処理を行って下さい。*/
    public static final String MSG_KEY_CANT_UPDATE_STORE_S1019 = "S1019";
    /**買掛締めされた伝票ですので修正できません。*/
    public static final String MSG_KEY_CANT_UPDATE_STORE_S1015 = "S1015";
    /**処理状態を確認して下さい。*/
    public static final String MSG_KEY_CHECK_PROCESS_DATA = "S1020";
    /**発注日が過去の場合は、参照のみ可能です。*/
    public static final String MSG_KEY_ORDER_ONLY_SEE = "H0125";
    /**本部発注データは存在しています。*/
    public static final String MSG_KEY_ORDER_DATA_PRESENT = "H0108";
    /**指定された会社、事業部では、企画の登録を行なえません。*/
    public static final String MSG_KEY_MESSAGE_DISPLAY = "T0043";
    /**対象となる企画データが存在しません。*/
    public static final String MSG_KEY_NO_DATA_DISPLAY = "T0071";
    /**複写元、複写先に同じ店舗は指定できません。*/
    public static final String MSG_KEY_TENIN_EQ_TENOUT = "T0070";
    /**明細が１件も入力されていないとき、データの更新は行なわれません。*/
    public static final String MSG_KEY_NO_DATA_INPUT = "T0058";
    /**更新、参照、削除処理では、店舗グループコードの指定はできません。*/
    public static final String MSG_KEY_NOT_SPECIFY_STORE_GROUP_CODE = "T0059";
    /**販売期間終了日より後の日付は指定できません。*/
    public static final String MSG_KEY_HANBAI_LESS_THAN_SIIRE = "T0084";
    /**企画商品に、販売期間が範囲外となる商品が発生します。*/
    public static final String MSG_KEY_HANBAI_NOT_EXIST = "T0085";
    /**既に<%S%>を過ぎているため、変更できません。*/
    public static final String MSG_KEY_NO_CHANGE = "T0089";
    /**<%S%>に登録されていません。*/
    public static final String MSG_KEY_NOT_REGISTERED_TO = "C0261";
    /**<%S%>処理が正常終了しました。*/
    public static final String MSG_KEY_PROCESS_SUCCESS = "C0203";
    /**<%S%>を入力し、F12(実行)キーを押してください。*/
    public static final String MSG_KEY_PROCESS_SUCCESS_SEARCH_EDIT = "C0201";
    /**伝票は確定されています。入力する場合は更新を選択して下さい。*/
    public static final String MSG_KEY_VOUCHER_HAS_BEEN_IDENTIFIED = "S1004";
    /**支払済の伝票ですので修正できません。*/
    public static final String MSG_KEY_CAN_NOT_MODIFY = "S1021";
    /**仕入締めされた伝票ですので修正できません。*/
    public static final String MSG_KEY_CAN_NOT_MODIFY_DOCUMENT = "S1014";
    /**指定された伝票は取消されています。*/
    public static final String MSG_KEY_SPEC_HAS_CANCEL = "C0017";
    /**<%S%>は全角<%S%>文字で入力してください。 */
    public static final String MSG_KEY_INPUT_DOUBLE_BYTE = "C0256";
    /**<%S%>は半角<%S%>文字で入力してください。 */
    public static final String MSG_KEY_INPUT_CHARACTER = "C0255";
    /**生鮮で扱えない部門です。*/
    public static final String MSG_KEY_NOT_HANDLE_FRESH = "S1258";
    /**この伝票は処理できません。(旧)生鮮発注仕入確定入力画面で処理してください。*/
    public static final String MSG_KEY_NOT_OLD_HANDLE_FRESH = "S1268";
    /**担当者コードが存在しません。*/
    public static final String MSG_KEY_TANTOCD_NOT_EXIST = "C0264";
    /**入力した担当者コードは無効です。*/
    public static final String MSG_KEY_TANTOCD_WAS_DELETE = "C0265";
    /**パスワードが不正です。*/
    public static final String MSG_KEY_PASSWORD_NOT_MATCH = "C0266";
    /**確定処理を行って下さい。*/
    public static final String MSG_KEY_CONFIRM_PROCESS = "S1019";
    /**重複データあり*/
    public static final String MSG_KEY_INPUT_DATA_IS_EXIST = "S5132";
    /**運用日が当伝票の処理可能日ではありません。*/
    public static final String MSG_KEY_UNYODATE_INVALID = "S5092";
    /**納品日≦発注日エラー*/
    public static final String MSG_KEY_NOHINDATE_HATSUDATE_INVALID = "S5093";
    /**指定された伝票は確定されています。*/
    public static final String MSG_KEY_DENPYO_CONFIRMING = "S1085";
    /**該当の伝票情報が存在しません。*/
    public static final String MSG_KEY_DENPYO_NOT_EXISTS = "S1086";
    /**取引先が商品センターの伝票は確定できません。*/
    public static final String MSG_KEY_TORIHIKI_NOT_CONFIRM = "S1097";
    /**総量発注データは更新できません。*/
    public static final String MSG_KEY_SORYO_HATSU_NOT_UPDATE = "S1201";
    /**納品予定日が入力された納品日と異なります。*/
    public static final String MSG_KEY_DIFFERENT_DILIVERY_SCHEDULED_DATE = "S1096";
    /**コントロールマスタのデータ取得処理に失敗しました。*/
    public static final String MSG_KEY_FAILED_TO_GET_DATA_MASTER = "U1042";
    /**登録処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_FATAL_INSERT = "U1038";
    /**年月日マスタのデータ取得処理に失敗しました。*/
    public static final String MSG_KEY_FAILED_TO_GET_DATE_MASTER = "U1039";
    /**更新処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_FATAL_UPDATE = "U1040";
    /**入力データが不正です。01～06を入力して下さい。*/
    public static final String MSG_KEY_INVALID_INPUT_0106 = "U1041";
    /**部門総合計と売上金額が一致しません。*/
    public static final String MSG_KEY_SALE_NOT_MATCH = "U1036";
    /**部門総合計の消費税と売上総計の消費税が一致しません*/
    public static final String MSG_KEY_BMNURI_TOTAL_TAX_NOT_EQUAL = "U1046";
    /**指定された事業部コードは入力できません。*/
    public static final String MSG_KEY_NOT_INPUT_DEPART_CODE = "U1033";
    /**指定された店舗コードは入力できません。*/
    public static final String MSG_KEY_NOT_INPUT_STORE_CODE = "U1032";
    /**指定された年月は年月日マスタに登録されてません。*/
    public static final String MSG_KEY_DATE_NOT_REGISTER_DATE_MASTER = "U1023";
    /**月別部門別予算が登録されてません。*/
    public static final String MSG_KEY_MONTHLY_DEPAR_NOT_REGISTER = "U1021";
    /**売上確定後は削除できません。*/
    public static final String MSG_KEY_CAN_NOT_DELETE = "U1037";
    /**入力された月間予算が月別部門別予算合計と一致しません。*/
    public static final String MSG_KEY_NOT_MATCH_BUGGET = "U1022";
    /**差異が０でありません。修正してください。*/
    public static final String MSG_KEY_NOT_DIFF_ZERO = "U1020";
    /**昨年同曜実績が存在しなかった為、処理を行いませんでした。*/
    public static final String MSG_KEY_NOT_EXIST_DAY = "U1028";
    /**入力された店舗は指定できません。*/
    public static final String MSG_KEY_NOT_STORE_INPUT = "U1035";
    /**予算確定月以前の年月は指定できません。*/
    public static final String MSG_KEY_NOT_INPUT_DATE_BUDGET_PRE_MON = "U1027";
    /**入力して下さい。*/
    public static final String MSG_KEY_INPUT_REQUIRED_ENTER = "C9002";
    /**2桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_TWO_DIGITS = "C9003";
    /**昨対反映処理を行いました。内容 他店舗実績取得処理を行いました。内容を確認し、F12(保存)キーを押してください。*/
    public static final String MSG_KEY_URJP0010_01_DATA_INVALID = "C9094";
    /**差異計算処理を行いました。内容を確認し、F12(保存)キーを押してください。*/
    public static final String MSG_KEY_URJP0010_02_DATA_INVALID = "C9095";
    /**他店舗実績取得処理を行いました。内容を確認し、F12(保存)キーを押してください。*/
    public static final String MSG_KEY_URJP0010_03_DATA_INVALID = "C9096";
    /**システム運用日以前の日付で入力して下さい。*/
    public static final String MSG_KEY_SEARCH_NOT_MATCH_TYPE = "C9008";
    /**条件に該当するレコードがありません。*/
    public static final String MSG_KEY_SEARCH_NO_RECORDS = "M0027";
    /**検索件数が多すぎます。条件を絞って検索してください。*/
    public static final String MSG_KEY_SEARCH_NUMBER_TOO_MANY = "M0028";
    /**半角5桁の下3桁が000以外になる値で入力して下さい。*/
    public static final String MSG_KEY_INPUT_KAI_JI_BM_REQUIRED = "M0029";
    /**取引先コードの範囲指定に誤りがあります。*/
    public static final String MSG_KEY_RANGE_ERROR = "K1001";
    /**企画マスタの販売期間外の日付は入力できません。*/
    public static final String MSG_KEY_DATE_SALE_PERIOD = "T0050";
    /**開始日＞終了日となっています。*/
    public static final String MSG_KEY_DATE_START_GREATE_THAN_END_DATE = "T0016";
    /**販売期間が終了しているため、この企画は変更・削除できません。*/
    public static final String MSG_KEY_DATE_SALE_ENDED = "T0086";
    /**企画マスタの発注期間外の日付は入力できません。*/
    public static final String MSG_KEY_DATE_PLAN_ORDER_PERIOD = "T0091";
    /**販売期間が終了しているため、この企画は変更・削除できません。*/
    public static final String MSG_KEY_DATE_SALE_AFTER_END = "T0086";
    /**指定された企画コードは、既に確定済みです。*/
    public static final String MSG_KEY_CODE_ALREADY_ESTABLISHED = "T0082";
    /**新規登録処理、複写処理が正常終了しました。*/
    public static final String MSG_KEY_CREATE_COPY_SUCCEED = "T0040";
    /**企画の部門と商品の部門が異なっています。*/
    public static final String MSG_KEY_CODE_IS_DIFFERENT = "T0042";
    /**更新時間を入力した場合、この項目は省略できません。*/
    public static final String MSG_KEY_OMISSION_ERROR = "K1020";
    /**時刻に誤りがあります。*/
    public static final String MSG_KEY_TIME_ERROR = "C0016";
    /**更新時間の範囲指定に誤りがあります。*/
    public static final String MSG_KEY_INPUT_TIME_ERROR = "K1021";
    /**再入力して下さい。*/
    public static final String MSG_KEY_RE_ENTER_NO_PARAM = "C9004";
    /**半角数字8桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_EIGHT_DIGITS = "C9005";
    /**納品日(FR) > 納品日(TO)となっています。*/
    public static final String MSG_KEY_ERROR_DELIVERY_DATE_COMPARE = "H0201";
    /**取引先(FR) > 取引先(TO)となっています。*/
    public static final String MSG_KEY_ERROR_TORIHIKI_FR_GREATER = "H0202";
    /**半角数字1桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_ONE_DIGITS = "C9006";
    /**支店を入力する場合は、取引先を同じにしてください。*/
    public static final String MSG_KEY_REQUIRED_TORIHIKI_SAME_ENTER = "H0124";
    /**支店(FR) > 支店(TO)となっています。*/
    public static final String MSG_KEY_ERROR_BRANCH_FR_GREATER = "H0203";
    /**登録されていません。*/
    public static final String MSG_KEY_NOT_REGISTERED_NO_PARAM = "C9007";
    /**条件の絞込みを行ってください。*/
    public static final String MSG_KEY_PLEASE_NARROW_CONDITION = "S5151";
    /**更新処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_UPDATE_FAIL = "S5152";
    /**新規登録処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_INSERT_FAIL = "C9055";
    /**削除処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_DELETE_FAIL = "C9056";
    /**未確定に戻す処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_UNDO_FAIL = "C9101";
    /**総量発注対象以外の伝票は処理できません。*/
    public static final String MSG_KEY_CANT_PROCESS_OTHER_SLIP = "H0200";
    /**仕入原価と移動原価の差が10倍を超えています。*/
    public static final String MSG_KEY_SMALLER_THAN = "S1260";
    /***/
    public static final String MSG_KEY_HAVE_NOT_DATA = "S0260";
    /**入力された桁数が大きすぎます。（７桁＋少数２桁）*/
    public static final String MSG_KEY_KENGENK_OVER_TANKA_MAX = "S5153";
    /**入力された桁数が大きすぎます。（７桁）*/
    public static final String MSG_KEY_KENBAIK_OVER_TANKA_MAX = "S5154";
    /**入力された桁数が大きすぎます。（原価金額）*/
    public static final String MSG_KEY_KENGENKKIN_OVER_TANKA_MAX = "S5155";
    /**入力された桁数が大きすぎます。（売価金額）*/
    public static final String MSG_KEY_KENBAIKKIN_OVER_TANKA_MAX = "S5156";
    /**入力された桁数が大きすぎます。（６桁＋少数２桁）*/
    public static final String MSG_KEY_VALUE_OVER_SURYO_MAX = "S5157";
    /**販売期間終了日より後の日付は指定できません。*/
    public static final String MSG_KEY_DATE_SALE_AFTER_PERIOD_END_DATE = "T0084";
    /**企画マスタで“チラシ無し”が指定された場合、“チラシ有り”の指定はできません。*/
    public static final String MSG_KEY_NO_FLYERS_IN_MASTER_PLAN = "T0045";
    /**店舗確定日より前の日付は指定できません。*/
    public static final String MSG_KEY_DATE_EARLIER_FIXED_DATE = "T0081";
    /**定番の売単価より、企画の売単価が大きい値になっています。*/
    public static final String MSG_KEY_PRICE_SALE_IS_LARGE = "T0044";
    /**販売期間中に、定番の売単価より、企画の売単価が大きい値になります。*/
    public static final String MSG_KEY_PRICE_SALE_COST_PLAN_IS_LARGE = "T0092";
    /**複写元にＭ＆Ｍの対象商品が含まれているため、複写できません。*/
    public static final String MSG_KEY_CAN_NOT_COPY = "T0094";
    /**複写元にＭ＆Ｍの対象商品が含まれているため、複写できません。*/
    public static final String MSG_KEY_ALREADY_SENT = "T0094";
    /**明細が無効です。*/
    public static final String MSG_KEY_SPECIFICATION_INVALID = "S1261";
    /**納品予定日以降に確定処理を行って下さい。（納品予定日＝<%S%>）*/
    public static final String MSG_KEY_DATE_OVER_UNYODATE = "S1084";
    /**半角数字9桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NINE_DIGITS = "C9009";
    /**半角数字で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NUMBER_CHARACTER = "C9010";
    /**整数９桁＋少数２桁以内で入力して下さい。*/
    public static final String MSG_KEY_INPUT_TWO_DIGITS_OR_NINE_DIGITS = "C9011";
    /**整数５桁＋少数１桁以内で入力して下さい。*/
    public static final String MSG_KEY_INPUT_5_INT_1_FRAC = "C8060";
    /**半角数字4桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_FOUR_DIGITS = "C9012";
    /**チラシ区分には、１または２入力して下さい。*/
    public static final String MSG_KEY_INPUT_REQUIRED_ENTER_ONE_OR_TWO = "C9013";
    /**リピート発注区分には、１または２入力して下さい。*/
    public static final String MSG_KEY_INPUT_REQUIRED_ENTER_REPEAT_ONE_OR_TWO = "C9014";
    /**店舗訂正不可区分には、１または２入力して下さい。*/
    public static final String MSG_KEY_INPUT_REQUIRED_ENTER_TEISEIKBN_ONE_OR_TWO = "C9015";
    /**ＰＯＰ発行区分には、１～４のいずれか入力して下さい。*/
    public static final String MSG_KEY_INPUT_REQUIRED_ENTER_POPKBN_ONE_OR_FOUR = "C9016";
    /**ストコン送信区分には、１または２のいずれか入力して下さい。*/
    public static final String MSG_KEY_INPUT_REQUIRED_ENTER_STCONKBN_ONE_OR_TWO = "C9017";
    /**事業部コードには、０１,０２,０３,１０,９０のいずれかを入力して下さい。*/
    public static final String MSG_KEY_INPUT_CODE_JIGUYOBU_ERROR = "C9018";
    /**企画マスタに登録されていません。*/
    public static final String MSG_KEY_INPUT_MASTER_PLAN_NOT_REGISTER = "C9019";
    /**店舗(FR) > 店舗(TO)となっています。*/
    public static final String MSG_KEY_TENCD_ERROR_COMPARE = "C9020";
    /**処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_PROCESSING_ERROR = "C9042";
    /**発注日(FR) > 発注日(TO)となっています。*/
    public static final String MSG_KEY_ERROR_ORDER_DATE_FR_GREATER = "H0205";
    /**部門コード(FR) > 部門コード(TO)となっています。*/
    public static final String MSG_KEY_ERROR_DEPARMENT_CODE_FR_GREATER = "H0206";
    /**発注処理実行中です。JOBが終了するまで新規登録処理は行うことができません。クリア(Ｆ３)ボタンを押下し、再入力してください。*/
    public static final String MSG_KEY_DATA_NOT_FOUND = "C0308";
    /**摘要は半角３０文字で入力してください。 */
    public static final String MSG_KEY_INPUT_TEXT_NOT_VALID = "C9053";
    /**納品日が納品予定日を超えています。*/
    public static final String MSG_KEY_NOHINBI_OVER_NOHINYOTEIBI = "S1265";
    /**納品日が発注日以前です。*/
    public static final String MSG_KEY_NOHINBI_BEFORE_HATTCHUBI = "S1266";
    /**納品日が運用日を越えています。*/
    public static final String MSG_KEY_NOHINBI_OVER_UNYOBI = "S1267";
    /**納品数に誤りがあります。*/
    public static final String MSG_KEY_NOHINSU_ERROR = "H0204";
    /**発効日が1ヶ月以上未来日です。よろしければ明細を入力し、F12(保存)キーを押してください。*/
    public static final String MSG_KEY_FUTURE_DAY_MORE_THAN_ONE_MONTH = "C9052";
    /**発効日が1ヶ月以上未来日です。よろしければ明細を入力し、F12(保存)キーを押してください。*/
    public static final String MSG_KEY_FUTURE_DAY_MORE_THAN_ONE_MONTH_INSERT = "C9052";
    /**発効日が1ヶ月以上未来日です。よろしければ修正個所を入力し、F12(保存)キーを押してください。*/
    public static final String MSG_KEY_FUTURE_DAY_MORE_THAN_ONE_MONTH_UPDATE = "C9054";
    /**入力データが不正です。振込予定金額がマイナスです。*/
    public static final String MSG_KEY_SHRKINGK_IS_NEGATIVE = "K1056";
    /**グリット入力データチェック処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_CHECK_INPUTDATA_FAIL = "K1057";
    /**入力データが不正です。（年月）*/
    public static final String MSG_KEY_INPUT_DATE_INVALID = "C9043";
    /**発注運用日が登録されていません。*/
    public static final String MSG_KEY_UNYOUBI_NOT_REGISTERED = "C9044";
    /**年月は先月～来々月の間で入力して下さい。*/
    public static final String MSG_KEY_INPUT_YEAR_MONTH_NOT_MATCH_TYPE = "C9045";
    /**企画商品マスタに登録されていません。*/
    public static final String MSG_KEY_KIKAKU_NOT_REGISTERED = "C9046";
    /**商品[GTIN]が登録されていません。*/
    public static final String MSG_KEY_JAN_NOT_REGISTERED = "C9047";
    /**部門コードが登録されていません。*/
    public static final String MSG_KEY_BMN_NOT_REGISTERED = "C9050";
    /**取引先名が登録されていません。*/
    public static final String MSG_KEY_TORIHIKI_NOT_REGISTERED = "H0237";
    /**単位が登録されていません。*/
    public static final String MSG_KEY_UNIT_NOT_REGISTERED = "C9061";
    /**半角数字（1～12)で入力して下さい。*/
    public static final String MSG_KEY_ENTER_1BYTE_NUMBER = "C9063";
    /**取引先が指定されている行番号を指定してください。*/
    public static final String MSG_KEY_SPECIFY_LINE_NUMBER = "T0056";

    public static final String MSG_KEY_SPECIFY_POP_LINE_NUMBER = "T0109";

    public static final String MSG_KEY_SPECIFY_KEIRYOKI_LINE_NUMBER = "T0110";
    /**定番の原単価より、企画の原単価が大きい値になっています。*/
    public static final String MSG_KEY_UNITPRICE_IS_LARGE = "T0057";
    /**定番の売単価より、企画の売単価が大きい値になっています。*/
    public static final String MSG_KEY_SALE_IS_LARGE = "T0044";
    /**販売期間中に、定番の原単価より、企画の原単価が大きい値になります。*/
    public static final String MSG_KEY_ORIGINAL_PRICE_IS_LARGE = "T0093";
    /**同一商品が登録済みです。　企画：<%S%>、売価：<%S%>、期間：<%S%>*/
    public static final String MSG_KEY_SAME_PRODUCT_IS_REGISTERED = "T0041";
    /**既にストコン送信済みです。更新された内容で再度、ストコンに送信されます。*/
    public static final String MSG_KEY_SUKOTON_ALREADY_SENT = "T2103";
    /**システム運用日が登録されていません。*/
    public static final String MSG_KEY_SYSTEM_DAY_NOT_REGISTERED = "T2104";
    /**複写区分には、１:親企画または２:企画を入力して下さい。*/
    public static final String MSG_KEY_ENTER_COPY_DIVISION_1_OR_2 = "T2105";
    /**半角数字3桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_THREE_DIGITS = "T2108";
    /**発注日より前の日付は指定できません。*/
    public static final String MSG_KEY_SPECIFY_EARLIER_THAN_ORDER_DAY = "T0106";
    /**複写先事業部が複写元事業部と異なる場合、発注日以下の入力は不可です。*/
    public static final String MSG_KEY_INPUT_ORDER_DATE_IMPOSSIBLE = "T2109";
    /**複写元事業部の親企画レコードが存在しません。*/
    public static final String MSG_KEY_PARENT_RECORD_ORIGINAL_DIVISION_NOT_EXIT = "T2110";
    /**複写元事業部の企画レコードが存在しません。*/
    public static final String MSG_KEY_PLAN_RECORD_ORIGINAL_DIVISION_NOT_EXIT = "T2111";
    /**名称マスタに登録されていません。*/
    public static final String MSG_KEY_MEIM_NOT_EXIST = "M0121";
    /**区分マスタに登録されていません。*/
    public static final String MSG_KEY_EOSKBN_NOT_EXIST = "M0122";
    /**明細部を編集し[保存]ボタンで確定してください。*/
    public static final String MSG_KEY_INFO_ACTION_EDIT_DATA = "T2107";
    /**半角5桁の下3桁が000以外になる値で入力して下さい。*/
    public static final String MSG_KEY_VALUE_THREE_DIGIT = "M0029";
    /**ＥＯＢ対象区分には１(ＥＯＢ対象)または２(ＥＯＢ対象外)入力して下さい。*/
    public static final String MSG_KEY_EOB_VALUE_INPUT_ONE_OR_TWO = "M0030";
    /**入力データが不正です。*/
    public static final String MSG_KEY_INPUT_INVALID = "C9066";
    /**このメンテをすると［販社用］店別商品メンテからの予約メンテが削除されます。基本情報履歴検索画面にてご確認下さい。*/
    public static final String MSG_KEY_CHECK_BASIC_INFO = "M0007";
    /**このメンテをすると<%S%>の予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_PERSONNEL = "M0006";
    /**<%S%>のデータの整合性が正しくありません。*/
    public static final String MSG_KEY_ERROR_INCORRECT_DATA = "M0004";
    /**対応する事業部コードが登録されていません。*/
    public static final String MSG_KEY_TENCD_NOT_REGISTER = "M0031";
    /**半角1桁（1:月曜日 2:火曜日 3:水曜日 4:木曜日 5:金曜日 6:土曜日 7:日曜日になる値で入力して下さい。*/
    public static final String MSG_KEY_LIMIT_WEEK_DAY = "M0032";
    /**半角数字2桁（01～12）になる値で入力して下さい。*/
    public static final String MSG_KEY_LIMIT_MONTH_DAY = "M0033";
    /**半角1桁（1:平日 2:祝祭日になる値で入力して下さい。*/
    public static final String MSG_KEY_PUBLIC_HOLIDAY_TWO_VALUE = "M0034";
    /**半角1桁（9:店休日 0:その他）になる値で入力して下さい。*/
    public static final String MSG_KEY_VALUE_ONE_OR_NINE = "M0035";
    /**半角1桁（1:店休日 0:その他）になる値で入力して下さい。*/
    public static final String MSG_KEY_VALUE_ONE_OR_ZERO = "M0036";
    /**パターンNOには、１～１０を入力して下さい。*/
    public static final String MSG_KEY_MIN_MAX_ONE_TEN = "M0037";
    /**同一商品が登録済みです。　企画：<%S%>、売価：<%S%>、期間：<%S%>*/
    public static final String MSG_KEY_SAME_PRODUCT_IS_REGISTER = "T0041";
    /**販売期間中に削除される商品です。　発効日：<%S%>*/
    public static final String MSG_KEY_PRODUCT_IS_REMOVED_SALE = "T0097";
    /**半角数字1桁（1:総量対象 9:対象外）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_IKATUFLG = "M0038";
    /**半角数字7桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_VALUE_NULL_ANK_ACCOUNT_NO = "M0039";
    /**半角数字6桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_SIX_DIGITS = "C9065";
    /**処理区分に誤りがあります。*/
    public static final String MSG_KEY_SYORI_JYOUTAI_KUBUN_INCORRECT = "H0107";
    /**「<%S%>」 から「<%S%>」 への親企画データ複写処理を行います。対象となる企画を選択してください。*/
    public static final String MSG_KEY_PARENT_DATA_COPY_PROCESS = "T2112";
    /**「<%S%>」 から「<%S%>」への企画データ複写処理を行います。対象となる企画を選択してください。*/
    public static final String MSG_KEY_PLAN_DATA_COPY_PROCESS = "T2113";
    /**発注数量＞仕入数量のため、伝票を確定後、店別移動確定入力を行ってください。*/
    public static final String MSG_KEY_ORDER_LARGER_INPUT = "S1245";
    /**過剰納品です。店別移動データは確定されますが、差額在庫が発生します。*/
    public static final String MSG_KEY_EXCESS_DELIVERY = "S1246";
    /**処理NO取得処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_GET_PRESENT_NO_FAIL = "K1058";
    /**入力データチェック処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_GET_INPUT_DATA = "K1059";
    /**前月取引が無い、又は前月残高＝ZEROの取引先です。*/
    public static final String MSG_KEY_NO_PREVIOUS_MONTH_DEAL = "K1048";
    /**新規登録処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_NO_DATA_RESULT_INSERT = "M0040";
    /**編集処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_NO_DATA_RESULT_UPDATE = "M0041";
    /**半角2桁で入力して下さい。*/
    public static final String MSG_KEY_ENTER_INPUT_TWO_DIGITS = "C9067";
    /**3桁で入力して下さい。*/
    public static final String MSG_KEY_ENTER_INPUT_THREE_DIGITS = "C9068";
    /**事業部を入力して下さい。*/
    public static final String MSG_KEY_ENTER_THE_DIVISION = "C9069";
    /**4桁で入力して下さい。*/
    public static final String MSG_KEY_ENTER_INPUT_FOUR_DIGITS = "C9070";
    /**半角4桁で入力して下さい。*/
    public static final String MSG_KEY_ENTER_INPUT_FOUR_CHARACTERS = "C9071";
    /**部門を入力して下さい。*/
    public static final String MSG_KEY_BMNCD_INPUT_REQUIRED = "C9072";
    /**中分類コードが登録されていません。*/
    public static final String MSG_KEY_CHUBRNCD_NOT_REGISTERED = "C9073";
    /**9桁で入力して下さい。*/
    public static final String MSG_KEY_ENTER_INPUT_NINE_DIGITS = "C9074";
    /**半角9桁で入力して下さい。*/
    public static final String MSG_KEY_ENTER_LENGTH_NINE_DIGITS = "C9075";
    /**入力された桁数が大きすぎます。（値上下金額）*/
    public static final String MSG_KEY_KINGAK_OVER_MAX = "S5158";
    /**値上下金額合計欄と入力した金額が一致していません。*/
    public static final String MSG_KEY_NOT_MATCH_DATA = "S1061";
    /**理由区分の入力が正しくありません。*/
    public static final String MSG_KEY_INCORRECT_LINE = "S1068";
    /**出力条件を入力して下さい。*/
    public static final String MSG_KEY_TENCD_INPUT_REQUIRED = "C9076";
    /**半角1桁（1:ＳＭドライ 2:ＳＭ日配 3:ＨＣセンター（第３・４） 4:ＳＭ全件）で入力して下さい。*/
    public static final String MSG_KEY_TEN_CD_INPUT_NOT_MATCH_TYPE = "C9077";
    /***/
    public static final String MSG_KEY_HATTSU_DATE_LAGER_THAN_UNYO_DATE = "S1264";
    /**Ｍ＆Ｍコードには５０００以上を指定してください。（５０００未満は店舗で使用）*/
    public static final String MSG_KEY_INPUT_MORE_THAN_5000 = "T0055";
    /**既にＰＯＳへデータが送信されているため、Ｍ＆Ｍ情報は変更できません。*/
    public static final String MSG_KEY_DATA_POS_SENT_ALREADY = "T0090";
    /**全角<%S%>文字で入力してください。*/
    public static final String MSG_KEY_INPUT_DOUBLE_CHARACTER = "T2114";
    /**半角<%S%>文字で入力してください。*/
    public static final String MSG_KEY_INPUT_IS_CHARACTER = "T2115";
    /**既にＭ＆Ｍコードが設定されています。Ｍ＆Ｍコード：<%S%>*/
    public static final String MSG_KEY_CHECK_CONFIG_MM_CODE = "T0052";
    /**Ｍ＆Ｍの対象になっていません。*/
    public static final String MSG_KEY_NOT_SUBJECT_OF_MM_CODE = "T0053";
    /**同一コードが既に入力されています。*/
    public static final String MSG_KEY_SAME_CODE_IS_ENTERED = "C0020";
    /**入力データが不正です。9桁までの整数値を入力して下さい。*/
    public static final String MSG_KEY_CAN_NOT_INPUT_MORE_THAN_NINE_DIGITS = "C9078";
    /**プロパティファイルのダミー店舗コード取得処理に失敗しました。*/
    public static final String MSG_KEY_DUMMY_TEN_CD_NULL = "C9079";
    /**入力データが不正です。今月の棚卸日を入力して下さい。*/
    public static final String MSG_KEY_INPUT_INVENTORY_DAY_IN_CURRENT_MONTH = "C9080";
    /**入力データが不正です。前回登録した棚卸日以降の日付を入力して下さい。*/
    public static final String MSG_KEY_INPUT_INVENTORY_DAY_AFTER_LAST_REGIST_DAY = "C9081";
    /**5桁で入力して下さい。*/
    public static final String MSG_KEY_ENTER_INPUT_FIVE_DIGITS = "C9083";
    /**複写先に既にレコードが存在しています。*/
    public static final String MSG_KEY_EXIST_IN_COPY_DESTINATION = "T0107";
    /**複写する企画を指定してください。*/
    public static final String MSG_KEY_SPECIFY_PROJECT_COPY = "T2116";
    /**発注年月は先月～来々月の間で入力して下さい。*/
    public static final String MSG_KEY_HACHU_FROM_TO_INPUT_NOT_MATCH_TYPE = "H0210";
    /**発注日(FR)を入力して下さい。*/
    public static final String MSG_KEY_HATDDST_INPUT_REQUIRED = "C9084";
    /**発注日(FR)を再入力して下さい。*/
    public static final String MSG_KEY_HATDDST_INPUT_NOT_DATE = "C9085";
    /**発注日(TO)を入力して下さい。*/
    public static final String MSG_KEY_HATDDED_INPUT_REQUIRED = "C9086";
    /**発注日(TO)を再入力して下さい。*/
    public static final String MSG_KEY_HATDDED_INPUT_NOT_DATE = "C9087";
    /**半角1桁（1:登録順 2:商品順 3:店舗順）で入力して下さい。*/
    public static final String MSG_KEY_SORT_INPUT_NOT_MATCH_TYPE = "C9088";
    /**会社が登録されていません。*/
    public static final String MSG_KEY_KAISYACD_NOT_REGISTERED = "C9089";
    /**事業部が登録されていません。*/
    public static final String MSG_KEY_JIGYOBUCD_NOT_REGISTERED = "C9090";
    /**店舗が登録されていません。*/
    public static final String MSG_KEY_TENCD_NOT_REGISTERED = "C9091";
    /**発注種類種別が登録されていません。*/
    public static final String MSG_KEY_HATSRUINM_NOT_REGISTERED = "C9092";
    /**会社を入力して下さい。*/
    public static final String MSG_KEY_KAISYA_INPUT_REQUIRED = "C9093";
    /**発注パターン情報（定番）が取引先マスターに登録されていません。*/
    public static final String MSG_KEY_TRICD_NOT_REGISTER = "H0216";
    /**企画コードまたは発注日を入力して下さい。*/
    public static final String MSG_KEY_ENTER_ORDER_DATE_PLANNING_CODE = "C9082";
    /**ＣＳＶファイル取得処理に失敗しました。*/
    public static final String MSG_KEY_CSV_REPORT_IS_NULL = "C9098";
    /**70:生鮮は入力出来ません。*/
    public static final String MSG_KEY_CANT_INPUT_NUMBER_70 = "H0211";
    /**78:緊急は入力出来ません。*/
    public static final String MSG_KEY_CANT_INPUT_NUMBER_78 = "H0212";
    /**79:事前は入力出来ません。*/
    public static final String MSG_KEY_CANT_INPUT_NUMBER_79 = "H0213";
    /**発注日より未来日で入力して下さい。*/
    public static final String MSG_KEY_INPUT_FUTURE_DATE_ORDER = "H0214";
    /**店舗グループコードが登録されていません。*/
    public static final String MSG_KEY_STORE_NOT_REGISTER = "H0215";
    /**ＥＯＳ・外注棚卸在庫高が変更されています。確認しF12(実行)キーを押して下さい。*/
    public static final String MSG_KEY_EOS_HAS_CHANGED = "Z0019";
    /**会社マスタに未登録です。*/
    public static final String MSG_KEY_KAISYA_MASTER_NOT_EXIST = "U1043";
    /**事業部マスタに未登録です。*/
    public static final String MSG_KEY_JIGYOBU_MASTER_NOT_EXIST = "U1044";
    /**店舗マスタに未登録です。*/
    public static final String MSG_KEY_TENPO_MASTER_NOT_EXIST = "U1045";
    /**１：前期また２：当期以外は入力できません。*/
    public static final String MSG_KEY_OUTPUT_KBN_1_2_INVALID = "Z0037";
    /**１：前期以外は入力できません。*/
    public static final String MSG_KEY_OUTPUT_KBN_1_INVALID = "Z0038";
    /**１：通常営業店舗のみ出力、２：全店舗出力以外は入力できません。*/
    public static final String MSG_KEY_OUTPUT_TEN_KBN_1_2_INVALID = "Z0039";
    /**部門別出力の場合、出店舗区分は入力できません。*/
    public static final String MSG_KEY_BMN_BETSU_TENOUT_KBN_INVALID = "Z0033";
    /**会社マスタに登録されていません。*/
    public static final String MSG_KEY_KAISYA_CODE_NOT_REGISTER = "M0042";
    /**事業所マスタに登録されていません。*/
    public static final String MSG_KEY_JIGYOBU_CODE_NOT_REGISTER = "M0043";
    /**基本情報マスタの整合性エラーの為、ＤＢ処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_FATAL_DATABASE_ERROR = "M0044";
    /**コピー先のデータが存在します。このデータにコピー機能は使用できません。*/
    public static final String MSG_KEY_DATA_EXIST_CANT_COPY_DATA = "M0045";
    /**コピーするデータがありません。*/
    public static final String MSG_KEY_NO_DATA_COPY = "M0046";
    /**店別商品マスタの整合性エラーの為、小分類コード取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_FATAL_ERROR_CONSISTENCY_STORE_PRODUCT = "M0047";
    /**このデータは削除済みデータです。*/
    public static final String MSG_KEY_DATA_IS_DELETE = "M0048";
    /**部門マスタに登録されていません。*/
    public static final String MSG_KEY_BUMON_CODE_NOT_REGISTER = "S5054";
    /**分類マスタに登録されていません。*/
    public static final String MSG_KEY_CHUBUNRUI_CODE_NOT_REGISTER = "S1007";
    /***/
    public static final String MSG_KEY_SHOBUNRUI_CODE_NOT_REGISTER = "M0051";
    /**分類マスタのデータの整合性が正しくありません。*/
    public static final String MSG_KEY_DATA_MASTER_INCORRECT = "M0052";
    /**メーカマスタに登録されていません。*/
    public static final String MSG_KEY_MANUFACTURER_CODE_NOT_REGISTER = "M0054";
    /**1桁（0:取引先マスタ参照、9:発注対象外）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NUMBER_ONE_DIGIT_ONE_OR_NINE = "M0055";
    /**1桁（1:Lサイズ 2:Sサイズ 4:生鮮 9:発行しない）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_ORDER_SIZE = "M0056";
    /**1桁（1:Ｐカード発注 3:日配ＯＢ 5:生鮮ＯＢ 9:販売のみ）で入力して下さい。*/
    public static final String MSG_KEY_SELL_DAILY_DELIVERED = "M0057";
    /**1桁（1,2,3,4:内税 5,6,7,8:外税 9:非課税 0:上位参照）で入力して下さい。*/
    public static final String MSG_KEY_NUMBER_CANT_INPUT_OF_TAXKBN = "M0058";
    /**1桁（1:不定貫商品 9:対象外）で入力して下さい。*/
    public static final String MSG_KEY_NUMBER_CANT_INPUT_OF_HUTEIKANKBN = "M0059";
    /**1桁（1:計算する。 2:計算しない。）で入力して下さい。*/
    public static final String MSG_KEY_NUMBER_CANT_INPUT_OF_UPKBN = "M0060";
    /**2桁（01:1便対応可 02:2便対応可 03:3便対応可 09:便対応不可）で入力して下さい。*/
    public static final String MSG_KEY_NUMBER_CANT_INPUT_OF_BINKBN = "M0061";
    /**取引先マスタに登録されていません。*/
    public static final String MSG_KEY_CUSTOMER_NOT_REGISTER = "S5053";
    /**半角数字１～１２をで入力して下さい。*/
    public static final String MSG_KEY_INPUT_OUT_NUMBER_1_TO_12 = "M0063";
    /**基本情報マスタの整合性エラーの為、基本情報マスタ登録処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_FATAL_ERROR_PROCESS_REGISTER = "M0064";
    /**店舗グループマスタに登録されていません。*/
    public static final String MSG_KEY_PATTERN_CODE_NOT_REGISTER = "M0065";
    /**半角3桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_THREE_DIGITS_LONG = "C9099";
    /**発注運用日以前の日付で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_DATE = "C9100";
    /**産地候補取得処理に失敗しました。*/
    public static final String MSG_KEY_FAIL_LOCATION_CANDIDATE = "H0225";
    /**確定処理を行ってください。*/
    public static final String MSG_KEY_CONFIRMATION_PROCESS = "H0226";
    /**更新（確定）処理に失敗しました。*/
    public static final String MSG_KEY_FAIL_PROCESS_UPDATE = "H0227";
    /**入力された桁数が大きすぎます。（仕入金額総合計）*/
    public static final String MSG_KEY_NUMBER_GROSS_TOTAL_TOO_LARGE = "H0228";
    /**仕入原価と移動原価の差が10倍を超えています。（７桁＋少数２桁）*/
    public static final String MSG_KEY_NINE_DIGIT_DIFERENCE_MORE_THAN_TEN = "H0229";
    /**入力された移動数の桁数が大きすぎます。（６桁＋少数２桁）（行-<%S%>）*/
    public static final String MSG_KEY_MOBILE_NUMBER_TOO_LARGE = "H0230";
    /**入力された項目に誤りがあります。（行-<%S%>）*/
    public static final String MSG_KEY_FAIL_INPUT_ITEM = "H0231";
    /**産地名が入力されていません。（行-<%S%>）*/
    public static final String MSG_KEY_FAIL_INPUT_NAME_LOCAL = "H0232";
    /**移動数計と仕入数計が等しくありません。*/
    public static final String MSG_KEY_IDO_DIFERENCE_KANBARASU = "H0233";
    /**産地メンテ処理に失敗しました。*/
    public static final String MSG_KEY_FAIL_LOCAL_MAINTENANCE = "H0234";
    /**店別商品マスタは会社マスタに登録されていません。*/
    public static final String MSG_KEY_SHOP_PRODUCT_NOT_REGISTER = "H0221";
    /**発注パターン情報（特売）が取引先マスターに登録されていません。*/
    public static final String MSG_KEY_PRODUCT_MASTER_SALE_NOT_REGISTER = "H0223";
    /**定番/特売検査処理に失敗しました。*/
    public static final String MSG_KEY_FAILED_KEY_CLASSIS = "H0224";
    /**取引先が一部変更されたため分納日が変る可能性があります。④送込画面で再度更新をしてください。*/
    public static final String MSG_KEY_DO_UPDATE_AGAIN = "T2117";
    /**<%S%>により入力値が小さいです。*/
    public static final String MSG_KEY_CC_MIN = "C8033";
    /**<%S%>により入力値が大きいです。*/
    public static final String MSG_KEY_CC_MAX = "C8032";
    /**最小桁数： <%S%>*/
    public static final String MSG_KEY_CC_MIN_LENGHT = "C8028";
    /**最大桁数： <%S%>*/
    public static final String MSG_KEY_CC_MAX_LENGHT = "C8029";
    /**この伝票は処理できません。(旧)生鮮発注仕入確定入力画面で処理してください。*/
    public static final String MSG_KEY_NOT_PROCESS_THIS_SLIP = "S1268";
    /**帳票を表示するときは、「ﾀﾞｳﾝﾛｰﾄﾞ」項目のリンクを選択してください。*/
    public static final String MSG_KEY_REPORT_LINK_DOWNLOAD = "C0206";
    /**店別商品マスタに登録されていません。（行-<%S%>）*/
    public static final String MSG_KEY_NOT_REGIS_PRODUCT_MASTER = "H0235";
    /**店部門マスタに登録されていません。（行-<%S%>）*/
    public static final String MSG_KEY_PLEASE_CONFIRM_PROCESS = "H0236";
    /**店舗送信済みのため、納品日は変更できません。*/
    public static final String MSG_KEY_CAN_NOT_CHANGE_NOHINDAY = "T2118";
    /**発注期間（終了日）[<%S%>]以前の日付で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_SIIRE_TODD = "T2119";
    /**同一Ｍ＆Ｍコードが登録済みです。　企画：<%S%>、部門：<%S%>、期間：<%S%>*/
    public static final String MSG_KEY_MM_CODE_IS_REGISTER = "T0054";
    /**納品日か入力日付を入力してください。*/
    public static final String MSG_KEY_ENTER_INPUT_DATE_OR_DELIVERY_DATE = "S1214";
    /**入力された桁数が大きすぎます。（仕入金額合計）*/
    public static final String MSG_KEY_TOTAL_DEPOSIT_TOO_LARGE = "H0238";
    /**入力された桁数が大きすぎます。（移動金額合計）*/
    public static final String MSG_KEY_TOTAL_AMOUNT_TOO_LARGE = "H0239";
    /**入力された桁数が大きすぎます。（移動金額総合計）*/
    public static final String MSG_KEY_GRAND_TOTAL_AMOUNT_TOO_LARGE = "H0240";
    /**定番売単価＜＞入力売単価*/
    public static final String MSG_KEY_ERROR_INPUT_BAITAN_NOT_EQUAL_SAVED_BAITAN = "S1240";
    /**店舗コード(開始)＞店舗コード(終了)となっています。                              */
    public static final String MSG_KEY_TEN_START_GREATE_THAN_TEN_END = "U1025";
    /**企画店別商品マスタに登録されていません。*/
    public static final String MSG_KEY_KIKAKU_NOT_REGISTERED_T002 = "T2123";
    /**企画商品Mで設定した納品日以外は指定できません。*/
    public static final String MSG_KEY_KIKAKU_NOT_EQUAL_NOHINDAY = "T2124";
    /**半角10文字で入力してください。*/
    public static final String MSG_KEY_FLG_INFO_MAX_LENGTH = "M0066";
    /**データ存在チェック処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_CHECK_ERROR = "C9102";
    /**データ出力処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_CHECK_INPUT_ERROR = "C9103";
    /**データ計算処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_CAL_INPUT_ERROR = "C9104";
    /**プロパティファイルの対象事業部コード取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_CHECK_PROPERTY_ERROR = "C9105";
    /**プロパティファイルのダミー店舗コード取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_CHECK_PROPERTY_TRICD_ERROR = "C9106";
    /**会社コード存在チェック処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_CHECK_KAISA_ERROR = "C9107";
    /**事業部コード存在チェック処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_CHECK_JIGO_ERROR = "C9108";
    /**店舗コード存在チェック処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_CHECK_TEN_ERROR = "C9109";
    /**予算確定情報取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_CHECK_KAKUTE_ERROR = "C9110";
    /**入力データが不正です。（半角数値7桁以下で入力して下さい。）*/
    public static final String MSG_KEY_DATA_CHECK_LENTH_ERROR = "C9111";
    /**入力データが不正です。（未入力です。半角数値7桁以下で入力して下さい。）*/
    public static final String MSG_KEY_DATA_CHECK_LENTH_ZERO_ERROR = "C9112";
    /**入力データが不正です。（マイナスは入力できません）*/
    public static final String MSG_KEY_DATA_PARSE_INT_ERROR = "C9113";
    /**全角/半角が混在しています。*/
    public static final String MSG_KEY_INPUT_NOT_VALID_1BYPTES_OR_2BYTES = "K1004";
    /**全角文字で入力してください。*/
    public static final String MSG_KEY_ZENKAKU_FAILED = "C8038";
    /**半角文字で入力してください。*/
    public static final String MSG_KEY_HANKAKU_FAILED = "C8039";
    /**入力データが不正です。1(店別)または2(部門別)を入力して下さい。*/
    public static final String MSG_KEY_INPUT_ERROR_DEPART_OR_STORE = "Z0040";
    /**キー項目のマスタ存在チェック処理に失敗しました。*/
    public static final String MSG_KEY_FAIL_TO_MASTER_EXITS = "Z0041";
    /**部門コード(FR) > 部門コード(TO)となっています。*/
    public static final String MSG_KEY_ERROR_BMNCD_COMPARE = "H0206";
    /**全角２０文字で入力して下さい。*/
    public static final String MSG_KEY_INPUT_TWENTY_DIGITS = "M0067";
    /**全角５文字で入力して下さい。*/
    public static final String MSG_KEY_INPUT_FIVE_DIGITS = "M0068";
    /**文字数オーバーです。*/
    public static final String MSG_KEY_INPUT_DIGIT_IS_OVER = "M0069";
    /**店コード(FR) > 店コード(TO)となっています。*/
    public static final String MSG_KEY_TENFROM_TENTO_COM_ERROR = "H0244";
    /**当月以前の年月を入力して下さい。*/
    public static final String MSG_KEY_INPUT_PREVIOUS_YEARS_MONTH = "C9114";
    /**入力データが不正です。在庫実績保存期間以前の年月は入力できません。*/
    public static final String MSG_KEY_INPUT_DATE_DATA_INVALID = "C9115";
    /**発注日以後の日付を入力して下さい。*/
    public static final String MSG_KEY_INPUT_DATE_AFTER_HACHU = "H0243";
    /**発注日から7日以内の日付を入力して下さい。*/
    public static final String MSG_KEY_INPUT_DATE_WITHIN_SEVEN_DAYS = "H0242";
    /**複写処理が正常終了しました。*/
    public static final String MSG_KEY_PROCESS_COPY_SUCCESS = "T2130";
    /**複写処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_COPY_FAIL = "T2131";
    /**７桁で入力して下さい。*/
    public static final String MSG_KEY_ENTER_INPUT_SEVEN_DIGITS = "M0070";
    /**運用日取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_NO_UNYODATE_DATA_RESULT = "A0050";
    /**在庫実績保存期間取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_NO_HOZONKIKAN_DATA_RESULT = "A0051";
    /**年度取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_NENDO_PROCESSING_ERROR = "A0052";
    /**出力年(From)の妥当性チェック処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_TAISYODATEYED_FROM_FAIL = "A0053";
    /**出力年(To)の妥当性チェック処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_TAISYODATEYED_TO_FAIL = "A0054";
    /**入力データが不正です。在庫実績保存期間より前の年月は入力できません。*/
    public static final String MSG_KEY_INPUT_TAISYODATEYED_FROM_NOT_VALID = "A0055";
    /**妥当性チェック処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_FAIL = "A0056";
    /**出力データの取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_PROCESSING_ERROR = "A0057";
    /**年月(開始)＞年月(終了)となっています。 */
    public static final String MSG_KEY_STARTDATE_IS_GREATER_ENDDATE = "A0001";
    /**当月以前の年月は入力できません。*/
    public static final String MSG_KEY_DATE_NOT_VALID = "U0009";
    /**店舗コード入力チェック処理に失敗しました。*/
    public static final String MSG_KEY_TENPO_PROCESSING_ERROR = "U0040";
    /**出力年入力チェック処理に失敗しました。*/
    public static final String MSG_KEY_YEAR_PROCESSING_ERROR = "U0041";
    /**出力月入力チェック処理に失敗しました。*/
    public static final String MSG_KEY_MONTH_PROCESSING_ERROR = "U0042";
    /**プロパティファイルのダミー店舗コード取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください*/
    public static final String MSG_KEY_FAIL_DUMMY_PROPERTY = "U0043";
    /**部門コード存在チェック処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_DATA_CHECK_BMNNAME_ERROR = "A0058";
    /**数値が誤っています。正の数を入力して下さい。*/
    public static final String MSG_KEY_POSITIVE_NUMBER = "T0037";
    /**指定された月次月が誤っています。*/
    public static final String MSG_KEY_MONTH_IS_INCORRECT = "S1057";
    /**リモートJOB起動処理が正常終了しました。*/
    public static final String MSG_KEY_PROCESS_JOB_SUCCESS = "Z0042";
    /**リモートJOB起動処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_PROCESS_JOB_FAIL = "Z0043";
    /**4桁の数値で入力して下さい。*/
    public static final String MSG_KEY_INPUT_FOURS_DIGITS = "Z0044";
    /**仕入月次処理と同日には処理できません。*/
    public static final String MSG_KEY_UNYODATE_EQUALS_SHIIREKIKUDATE = "Z0045";
    /**「<%S%>店」のストコン送信済フラグクリア処理を行います。対象となる企画を選択してください。*/
    public static final String MSG_KEY_CLEAR_SUTOKON_PROCESS = "T2133";
    /**「<%S%>店」の店舗送信済フラグクリア処理を行います。対象となる企画を選択してください。*/
    public static final String MSG_KEY_CLEAR_STORE_PROCESS = "T2134";
    /**「<%S%>店」から「<%S%>店」への企画データ複写処理を行います。対象となる企画を選択してください。*/
    public static final String MSG_KEY_PLANING_COPY_DATA = "T2135";
    /**送信済フラグクリア処理に失敗しました。*/
    public static final String MSG_KEY_FAIL_CLEAR_SUTOKON_PROCESS = "T2136";
    /**”００”で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_KAISYA_CD_VALUE_00 = "M0071";
    /**コピー先店舗グループコードは店舗グループコードと異なる値で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_GRP_EQUAL_GRPCP = "M0072";
    /**半角1桁（1:売価還元法 2:移動平均原価法 3:原価在庫 4:０在庫 5:０在庫（荒利無し））で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_BAIKAN_KBN = "M0073";
    /**半角1桁（1：売仕対象　2：売仕対象（手書伝票） 9：対象外）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_URI_KBN = "M0074";
    /**半角1桁（1：酒税対象　9：対象外）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_SAKETAX_KBN = "M0075";
    /**半角数字1桁（0：上位参照　1,2,3,4：内税　5,6,7,8：外税　9：課税しない）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_TAX_KBN = "M0076";
    /**指定された店・部門が店部門マスタに登録されていません。*/
    public static final String MSG_KEY_NOT_REGISTERED_BAIKAN_KBN = "Z0046";
    /**JANコードが登録されていません。*/
    public static final String MSG_KEY_JANCD_NOT_REGISTERED = "M0084";
    /**オーダブック明細行マスタに登録されていません。*/
    public static final String MSG_KEY_NOT_REGISTERED_IN_ORDER_BOOK = "H0245";
    /**商品コードが<%S%>行目にすでに登録されています。*/
    public static final String MSG_KEY_JANCD_REGISTERED_TO = "H0246";
    /**既に店舗が999件登録されている為、これ以上登録できません。*/
    public static final String MSG_KEY_GREATER_OR_EQUAL_999_PARAM = "M0077";
    /**店舗区分は、半角数字1桁(1:店舗 3:センター 8:総量店舗 9:本部)を入力して下さい。*/
    public static final String MSG_KEY_INPUT_REQUIRED_TEN_KBN = "M0078";
    /**半角数字1桁（1:大型店 2:スーパーマーケット 3:専門店）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_KIBO_KBN = "M0079";
    /**半角数字1桁（1:大阪）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_AREA_CD = "M0080";
    /**半角数字1桁（2:TEC）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_POS_TYP_KBN = "M0081";
    /**半角数字1桁（1:休止 9:通常）で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_KYUSI_KBN = "M0082";
    /**半角数字１〜９９９で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_HW_TANA_NO_ST = "M0083";
    /**データ取得処理に失敗しました。*/
    public static final String MSG_KEY_IS_NULL_OR_EMPTY_DATA = "Z0047";
    /**編集取得処理に失敗しました。*/
    public static final String MSG_KEY_IS_NULL_OR_EMPTY_COMPILATION = "Z0048";
    /**入力データが不正です。0または1を入力して下さい。*/
    public static final String MSG_KEY_INPUT_DATA_INVALID_0_1 = "Z0049";
    /**入力データが不正です。棚卸をする場合は1を、しない場合は0を入力して下さい。*/
    public static final String MSG_KEY_INPUT_DATA_INVALID_1_0 = "Z0050";
    /**棚卸取扱部門表データ存在有無取得処理に失敗しました。*/
    public static final String MSG_KEY_IS_NULL_OR_EMPTY_INVENTORIES = "Z0051";
    /**このメンテをすると新規<%S%>発効日: <%S%>の予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_PERSONNEL_CREATE = "M0085";
    /**このメンテをすると更新<%S%>発効日: <%S%>の予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_PERSONNEL_UPDATE = "M0086";
    /**このメンテをすると削除<%S%>発効日: <%S%>の予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_PERSONNEL_DELETE = "M0087";
    /**半角で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_1BYTE = "M0088";
    /**半角数字０～９９数字で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_HANBAI_KIGEN_DAY_0_TO_99 = "M0090";
    /**半角7桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_MKR_7_1BYTE = "M0092";
    /**半角数字０～９９９．９で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_TAXTIRU_0_TO_999 = "M0093";
    /**数字5桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_5_DIGIT = "M0094";
    /**8桁以内で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_8_DIGIT = "M0095";
    /**数字5桁以内で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_5_DIGIT_NUMBER = "M0096";
    /**数字<%S%>桁で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_FORMAT_DIGIT_NUMBER = "M0097";
    /**正の値（0以）上で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_GREATER_0 = "M0098";
    /**出力年の妥当性チェック処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_TAISYODATE_Y_FAIL = "A0059";
    /**出力月の妥当性チェック処理に失敗しました。*/
    public static final String MSG_KEY_PROCESS_TAISYODATE_M_FAIL = "A0060";
    /**出力データの計算処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_CALCULATION_ERROR = "U0044";
    /**入力データが不正です。指定された年度のデータは出力できません。*/
    public static final String MSG_KEY_YEAR_ERROR = "U0045";
    /**予算実績の計算処理処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_YEAR_FATAL_PROCESSING_ERROR = "U0046";
    /**担当者情報取得処理に失敗しました。*/
    public static final String MSG_KEY_FAIL_CONTACT_INFO_PROCESS = "U0047";
    /**商品名は全角２０文字で入力してください。*/
    public static final String MSG_KEY_PRODUCT_NAME_NOT_DOUBLE_BYTE = "S5159";
    /**担当者名が登録されていません。*/
    public static final String MSG_KEY_TANTOCD_NOT_REGISTERED = "M0099";
    /**買掛金元帳作成処理はスタートしております。画面を閉じてお待ちください。*/
    public static final String MSG_KEY_PROCESS_ACCOUNTS_PAYABLE_CREATION_START = "K1060";
    /**買掛金元帳作成処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_PROCESS_ACCOUNTS_PAYABLE_CREATION_FAIL = "K1061";
    /**運用日が３月・９月以外、棚卸確定月が３月・９月以外の場合、[１：前期]のみが選択できます。*/
    public static final String MSG_KEY_OUTPUT_KBN_1_INVALID_WHEN_MONTH_03_OR_09 = "Z0052";
    /**年月（開始）＞年月（終了）となっています。*/
    public static final String MSG_KEY_DATE_START_LARGER_DATE_END = "S1067";
    /**F12(実行)プロセス処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_ERROR_F12_PROCESS = "U0048";
    /**入力値が誤っています。*/
    public static final String MSG_KEY_INPUT_VALUE_INCORRECT = "Z0009";
    /**取引先別在庫ＣＳＶ出力を開始しました。*/
    public static final String MSG_KEY_STRADING_STOCK_DESTINATION_CSV_OUTPUT = "Z0010";
    /**取引先別在庫金額一覧作成処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_STRADING_STOCK_DESTINATION_CSV_OUTPUT_FAIL = "Z0053";
    /**帳票のヘッダ設定*/
    public static final String MSG_KEY_NOT_DATA_PAGE_ERROR = "C9125";
    /**事業部、部門、店舗グループを選択してください。*/
    public static final String MSG_KEY_JIGO_TEN_TENGRP_NOT_REGISTERED = "C9126";
    /**発注パターン情報（定番/特売検査）が取引先マスターに登録されていません。*/
    public static final String MSG_KEY_INPUT_PATTERN_ALL_ERROR = "C9124";
    /**月間予算取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_INPUT_YOKINMON_DATA_INVALID = "C9116";
    /**初期データセット処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_INPUT_MAXDAY_DATA_INVALID = "C9117";
    /**参照他店舗存在チェック処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_INPUT_EXTENNM_DATA_INVALID = "C9118";
    /**入力データが不正です。（半角数値を入力して下さい。）*/
    public static final String MSG_KEY_INPUT_NUMBER_DATA_INVALID = "C9119";
    /**担当者情報取得処理中に致命的なエラーが発生しました。システム担当者に連絡してください。*/
    public static final String MSG_KEY_INPUT_TANTO_DATA_INVALID = "C9120";
    /**担当者情報取得処理に失敗しました。*/
    public static final String MSG_KEY_INPUT_TANTO_DATA_EMPTY = "C9121";
    /**ＥＯＳ区分が対象外です。基本情報ＥＯＳ区分：<%S%>*/
    public static final String MSG_KEY_EOS_WARNING = "T2137";
    /**ＥＯＳ区分が対象外です。基本情報ＥＯＳ区分：<%S%>　取引先ＥＯＳ区分：<%S%>*/
    public static final String MSG_KEY_EOS_WARNING_TWO = "T2138";
    /**削除データ取得処理に失敗しました。*/
    public static final String MSG_KEY_GET_DELETE_DATA_FAIL = "K1063";
    /**半角40桁以内で入力して下さい。*/
    public static final String MSG_KEY_INPUT_TEXT_1BYTE_LIMIT40 = "K1064";
    /**全角20桁以内で入力して下さい。*/
    public static final String MSG_KEY_INPUT_TEXT_2BYTE_LIMIT20 = "K1065";
    /**行<%S%>の原単価: 定番の原単価より、企画の原単価が大きい値になっています。*/
    public static final String MSG_KEY_UNITPRICE_IS_LARGE_GENK = "T2139";
    /**行<%S%>の原単価 : 販売期間中に、定番の原単価より、企画の原単価が大きい値になります。*/
    public static final String MSG_KEY_ORIGINAL_PRICE_IS_LARGE_GENK = "T2141";
    /**行<%S%>の売単価: 定番の売単価より、企画の売単価が大きい値になっています。*/
    public static final String MSG_KEY_SALE_IS_LARGE_BAIK = "T2140";
    /**行<%S%>の売単価: 販売期間中に、定番の売単価より、企画の売単価が大きい値になります。*/
    public static final String MSG_KEY_PRICE_SALE_COST_PLAN_IS_LARGE_BAIK = "T2142";
    /**正の整数(0以上)の整数7桁、小数2桁(半角文字)で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_GENK = "M0100";
    /**正の整数(0以上)の整数7桁(半角文字)で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_BAIK = "M0101";
    /**0より大きい整数の整数部6桁、小数部1桁(半角文字)で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_HATTYU_IRISU = "M0102";
    /**産地名は全角文字で入力して下さい。（行-<%S%>）*/
    public static final String MSG_KEY_INPUT_TEXT_DOUBLE_BYTE = "H0247";
    /**産地名が重複しています。（行-<%S%>）*/
    public static final String MSG_KEY_TEXT_DUPLICATED = "H0248";
    /**cc.app.cm.exception.update_no_record*/
    public static final String MSG_KEY_EXCEPTION_UPDATE_NO_RECORD = "cc.app.cm.exception.update_no_record";
    /**cc.app.cm.exception.insert_no_record*/
    public static final String MSG_KEY_EXCEPTION_INSERT_NO_RECORD = "cc.app.cm.exception.insert_no_record";
    /**cc.app.cm.exception.delete_no_record*/
    public static final String MSG_KEY_EXCEPTION_DELETE_NO_RECORD = "cc.app.cm.exception.delete_no_record";
    /**cc.app.cm.exception.file_format_error*/
    public static final String MSG_KEY_EXCEPTION_FILE_FORMAT_ERROR = "cc.app.cm.exception.file_format_error";
    /**cc.app.cm.exception.file_format_error*/
    public static final String MSG_KEY_EXCEPTION_UPLOAD_FOLDER_NOT_EXIST = "cc.app.cm.exception.upload_folder_not_exist";
    /**数字2桁(ポップアップに表示されるいずれかの値)で入力して下さい。*/
    public static final String MSG_KEY_INPUT_NOT_MATCH_TYPE_TANI = "M0103";
    /**事業部コード：<%S%> 対応する分類情報を持つレコードが分類マスタに登録されていません。*/
    public static final String MSG_KEY_NOT_REGISTERED_FOR_BMN = "M0104";
    /**[000]値の以外を入力してください。*/
    public static final String MSG_KEY_INPUT_TENCD_INVALID = "C9134";
    /**部門(FR) > 部門(TO)となっています。*/
    public static final String MSG_KEY_BMN_ERROR_COMPARE = "H0249";
    /**発注日(FR)は発注運用日以前の日付で入力して下さい。*/
    public static final String MSG_KEY_NOT_MATCH_TYPE_FROM_GREATER = "H0250";
    /**発注日(FR)は発注運用日以前の日付で入力して下さい。*/
    public static final String MSG_KEY_KIHON_NOT_EXIST = "H0101";
    /** このメンテをすると［販社用］ＰＯＰ／ＰＯＳ情報メンテからの予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_POPPOS_FUTURE_DATA = "M0105";
    /** このメンテをすると新規<%S%>発効日: <%S%>の予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_POPPOS_CREATE = "M0106";
    /** このメンテをすると更新<%S%>発効日: <%S%>の予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_POPPOS_UPDATE = "M0107";
    /** このメンテをすると削除<%S%>発効日: <%S%>の予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_POPPOS_DELETE = "M0108";
    /** このメンテをすると［販社用］計量器情報メンテからの予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_KEIRYOKI_FUTURE_DATA = "M0109";
    /** このメンテをすると新規<%S%>発効日: <%S%>の予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_KEIRYOKI_CREATE = "M0110";
    /** このメンテをすると更新<%S%>発効日: <%S%>の予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_KEIRYOKI_UPDATE = "M0111";
    /** このメンテをすると削除<%S%>発効日: <%S%>の予約メンテが削除されます。入力担当者 <%S%>にご確認下さい。*/
    public static final String MSG_KEY_CHECK_KEIRYOKI_DELETE = "M0112";
    /** -99～99で入力して下さい */
    public static final String MSG_KEY_SPECIFY_ONDO_NUMBER = "E0001";
    /** 0〜9999999999で入力して下さい。 */
    public static final String MSG_KEY_SPECIFY_GUEST_NUMBER = "E0002";
    /** この商品には、予約メンテがあります。削除できません。「基本情報履歴検索」画面でご確認下さい。 */
    public static final String MSG_KEY_JAN_CAN_NOT_BE_DELETED = "M0008";
    /** 大部門コードが登録されていません。 */
    public static final String MSG_KEY_DAIBMN_NOT_INSERT = "M0113";
    /** 税率は正の整数（整数部３桁＋小数部１桁）で入力して下さい。 */
    public static final String MSG_KEY_TAXRATE_INPUT = "M0114";
    /** 分類マスタに存在する部門コードは削除できません。 */
    public static final String MSG_KEY_CANNOT_DELETE_BMNCD = "M0115";
    /** このデータは企画商品マスタに存在するため、削除できません。 */
    public static final String MSG_KEY_JAN_LOCATED_CAN_NOT_BE_DELETED = "M0023";
    /** 企画コードが登録されていません。 */
    public static final String MSG_KEY_KIKAKU_CD_NOT_INSERT = "T0111";
    /** 年度は半角数字で入力して下さい。 */
    public static final String MSG_KEY_NENDO_HANKAKU_INPUT = "T0112";
    /** 企画コードは半角数字で入力して下さい。 */
    public static final String MSG_KEY_KIKAKU_HANKAKU_INPUT = "T0113";
    /** ファイルの指定に誤りがあります。 */
    public static final String MSG_KEY_UPLOAD_FILE_MISTAKE = "H0251";
    /** ファイルアップロードチェック処理中に致命的なエラーが発生しました。システム担当者に連絡してください。 */
    public static final String MSG_KEY_CHECK_UPFILE_FAILED = "H0252";
    /** アップロード処理が正常終了しました。 */
    public static final String MSG_KEY_UPLOAD_SUCCEED = "H0253";
    /** ファイルオープンエラー */
    public static final String MSG_KEY_OPEN_UPFILE_ERROR = "H0254";
    /** ファイル読み込みエラー(%行目 */
    public static final String MSG_KEY_READ_UPFILE_ERROR = "H0255";
    /** 致命的エラー(%行目) */
    public static final String MSG_KEY_FATAL_UPFILE_ERROR = "H0256";
    /** フォーマットを確認し、ファイルを再入力して下さい。 */
    public static final String MSG_KEY_UPFILE_FORMAT_ERROR = "H0258";
    /** 半角数字０～２９数字で入力して下さい。 */
    public static final String MSG_KEY_029DAYS_INPUT_ERROR = "Z0054";
    /** 半角数字０～３０数字で入力して下さい。 */
    public static final String MSG_KEY_030DAYS_INPUT_ERROR = "Z0055";
    /** 半角数字０～３１数字で入力して下さい。 */
    public static final String MSG_KEY_031DAYS_INPUT_ERROR = "Z0056";
    /** 棚卸対象月ではありません。 */
    public static final String MSG_KEY_INVENTORY_MONTH_ERROR = "Z0057";
    /** 文字数が上限を超えています。 */
    public static final String MSG_KEY_CHARNUMBER_LIMIT_ERROR = "M0116";
    /** 関連する企画が存在します。 */
    public static final String MSG_KEY_KANREN_KIKAKU_EXIST = "T0114";
    /** <%S%>(<%S%>)*/
    public static final String MSG_KEY_KIKAKU_NM_BMN_NM = "T0115";
    /** 換算商品の対象の以外を入力してください。 */
    public static final String MSG_KEY_CHANGED_PRODUCT_INPUT = "M0118";
    /** 最小値： <%S%> */
    public static final String MSG_KEY_MIN_VALUE = "M0119";
    /** 最大値： <%S%> */
    public static final String MSG_KEY_MAX_VALUE = "M0120";
    /** テナントマスタに登録されていません。 */
    public static final String MSG_KEY_TENANT_NOT_REGISTERED = "TE001";
    /** <%S%>＞<%S%>となっています。 */
    public static final String MSG_KEY_GREATER_VALUE = "C0301";
    /** 歩合<%S%>より大きい金額を入力してください。 */
    public static final String MSG_KEY_COMMISSION_COMPARE_FAILED = "Y0001";
    /** 歩合<%S%>と同じ金額を入力してください。 */
    public static final String MSG_KEY_LAST_COMMISSION_INPUT_FAILED = "Y0002";
    /** 同一テナントで相殺の種類が24件以上存在します。システム管理者に問い合わせしてください。 */
    public static final String MSG_KEY_TENANT_SOUSAI_LIMIT_OVER = "Y0010";
    /** 預金種別が不正です。取引先マスタの設定を確認してください。 */
    public static final String MSG_KEY_DEPOSIT_TYPE_INVALID = "Y0003";
    /** 計上日は、運用日から±１ヶ月範囲で入力してください。 */
    public static final String MSG_KEY_KEIJYOU_DATE_SCOPE_FAILED = "Y0014";
    /** 締年月日 ≧ 計上日となります。 */
    public static final String MSG_KEY_SIMEI_DATE_INPUT_FAILED = "Y0015";
    /** 前回精算締日 >= 計上日となります。 */
    public static final String MSG_KEY_SEISAN_SIMEI_DATE_FAILED = "Y0016";
    /** 修正対象外のテナント相殺データは修正された。 */
    public static final String MSG_KEY_TENANT_EDITED_FAILED = "Y0019";
    /** 252バイト以上は入力できません。 */
    public static final String MSG_KEY_252CHARNUMBER_LIMIT_ERROR = "E0003";
    /** 256バイト以上は入力できません。 */
    public static final String MSG_KEY_256CHARNUMBER_LIMIT_ERROR = "E0004";
    /** 精算処理対象の年月が誤っています。 */
    public static final String MSG_KEY_SEISAN_YYYYDD_ERROR = "Y0026";
    /** ほかの担当者が処理中の為、ダウンロードできません。時間をおいて再度処理を行ってください。 */
    public static final String MSG_KEY_DOWNLOAD_EXCLUSIVE_CHECK = "X0001";
    /** CSVファイルが存在しません。 */
    public static final String MSG_KEY_CSV_FILE_NOT_EXIST = "H0260";
    /** CSVファイルが存在しません。 */
    public static final String MSG_KEY_ORDER_ENTRY_HAS_BEEN_CLOSE = "E0107";
    /** ファイルレイアウトが正しくないです。(<%S%>行、<%S%>目項目) */
    public static final String MSG_KEY_UPFILE_ITEM_FORMAT_ERROR = "H0261";
    /** テナント部門は移動できません。 */
    public static final String MSG_KEY_TENANTBMN_NOT_IDOU = "M0123";
    /** 発注入力は締め切られています。 */
    public static final String MSG_KEY_HATCHU_CLOSING = "H0262";
    /** 未入力項目のいずれかを入力して下さい。*/
    public static final String MSG_KEY_ITEM_NOT_INSERT = "S0262";
    /**<%S%>のコードが存在しません。*/
    public static final String MSG_KEY_MULTICODE_NOT_REGISTERD = "NM001";
    /**<%S%>のコードの桁数エラー。*/
    public static final String MSG_KEY_MULTICODE_CHARNUMBER_LIMIT_ERROR = "NM002";
    /**入力値 > To項目*/
    public static final String MSG_KEY_GREATER_TO = "C8036";
    /**入力値 < From項目*/
    public static final String MSG_KEY_GREATER_EQUAL = "C8037";
    /** 日付の指定に誤りがあります。 */
    public static final String MSG_KEY_DATE_FAILED_VALUE = "C8031";
    /** HHT委託精算取込データに登録されていません。 */
    public static final String MSG_KEY_H701KZIN_TANTOCD_NOT_REGISTERED = "NM012";
    /** HHT基準在庫取込データが登録されていません。 */
    public static final String MSG_KEY_HHT_KIHON_ZAIKO_NOT_REGISTERED = "NM013";
    /**発注取込データが登録されていません。」メッセージが表示される。 */
    public static final String MSG_KEY_ORDER_DATA_NOT_REGISTERD = "NM003";
    
    /** <%S%>～<%S%>で入力して下さい。  */
    public static final String MSG_KEY_NUMBER_RANGE_INVALID = "NM015";
    
    /** <%S%>バイト以上は入力できません。  */
    public static final String MSG_KEY_BYTE_RANGE_INVALID = "NM016";

    /**発注サイクルが登録されていません。 */
    public static final String MSG_KEY_ORDER_CYCLE_NOT_REGISTERD = "NM011";
    
    /**登録されていません。 */
    public static final String MSG_KEY_OUTPUT_OPTION_NOT_SELECTED = "NM004";
    
    /**HHT検品取込データが登録されていません。*/
    public static final String MSG_KEY_HTT_KENPIN_TORIKOMI_NOT_REGISTERD = "NM017";
    
    /**委託精算データが登録されていません。 */
    public static final String MSG_KEY_ITAKUSEISAN_NOT_REGISTERD = "NM014";

    /**区分マスタにグリードの最大表示明細数が登録されていません。 */
    public static final String MSG_KEY_GRID_NUMBER_REACH_LIMIT = "NM007";

    /**最大表示数を超えたので、条件を追加し、再実行してください。*/
    public static final String MSG_KEY_OVER_MAX_NUMBER_RECORDS = "NM006";
    
    /**HHT仕入返品取込データが登録されていません。*/
    public static final String MSG_KEY_HENHINTORIKOMI_NOT_REGISTERD = "NM018";
    
    /**納品予定日終了は納品予定日より未来日で入力して下さい。*/
    public static final String MSG_KEY_NOHINYOTE_DATE_GREATER = "NM008";
    
    /**HHT検品取込データに登録されていません。*/
    public static final String MSG_KEY_S702HNBR_TANTOCD_NOT_REGISTERED = "NM026";
    
    /**取引先が登録されていません。*/
    public static final String MSG_KEY_CUSTOMER_NOT_REGISTERED = "NM022";
    
    /**EOS伝票累積ファイル, 手書伝票累積ﾌｧｲﾙ, 移動伝票累積ﾌｧｲﾙが登録されていません。*/
    public static final String MSG_KEY_SLIP_CUMULATIVE_FILES_NOT_REGISTERED = "NM023";
    
    /**EOS伝票累積ファイル, 手書伝票累積ﾌｧｲﾙが登録されていません。*/
    public static final String MSG_KEY_EOS_HANDWRITTEN_FILES_NOT_REGISTERED = "NM024";
    
    /**移動伝票累積ﾌｧｲﾙが登録されていません。*/
    public static final String MSG_KEY_MOVE_SLIP_FILE_NOT_REGISTERED = "NM025";
    
    /**HHT品振取込データが登録されていません。*/
    public static final String MSG_KEY_S702HNBR_NOT_REGISTERED = "NM027";
    /**オリジナル商品発注データが登録されていません。*/
    public static final String MSG_KEY_ORIGINAL_GOODS_ORDER_DATA_NOT_REGISTERD = "NM019";
    /**商品別棚卸データが登録されていません。*/
    public static final String MSG_KEY_Z000SHNT_NOT_REGISTERED = "NM028";
    /**対象を選択してください。*/
    public static final String MSG_KEY_OUTPUT_OBJECT_NOT_INPUT = "NM029";
    /** コードが存在しません。 */
    public static final String MSG_KEY_CODE_NOT_EXIST = "C8045";
    
    /**HHT棚卸取込データに登録されていません。*/
    public static final String MSG_KEY_Z703TANA_TANTOCD_NOT_REGISTERED = "NM999";
    /**HHT委託精算取込データに登録されていません。*/
    public static final String MSG_KEY_S703ITSS_TANTOCD_NOT_REGISTERED = "NM005";
    /**品振数＞発注数となっています。*/
    public static final String MSG_KEY_ERROR_SINAFUSU_HACCHUSU_GREATER = "NM020";
    /**品振数＞前日在庫数となっています。*/
    public static final String MSG_KEY_ERROR_SINAFUSU_ZAIKOSU_GREATER = "NM021";
    
    
}
