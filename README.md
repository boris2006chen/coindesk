Java:8
資料庫:H2(OpenJPA / Spring Data JPA)
功能簡述:
1. 呼叫 coindesk API,解析其下行內容與資料轉換,並實作新的 API。
coindesk API:https://api.coindesk.com/v1/bpi/currentprice.json
2. 建立一張幣別與其對應中文名稱的資料表(需附建立 SQL 語法),並提
供 查詢 / 新增 / 修改 / 刪除 功能 API。
實作內容:
1. 幣別 DB 維護功能。
2. 呼叫 coindesk 的 API。
3. 呼叫 coindesk 的 API,並進行資料轉換,組成新 API。
此新 API 提供:
A. 更新時間(時間格式範例:1990/01/01 00:00:00)。
B. 幣別相關資訊(幣別,幣別中文名稱,以及匯率)。
請撰寫以下各項功能之單元測試:
1. 測試呼叫查詢幣別對應表資料 API,並顯示其內容。
2. 測試呼叫新增幣別對應表資料 API。
3. 測試呼叫更新幣別對應表資料 API,並顯示其內容。
4. 測試呼叫刪除幣別對應表資料 API。
5. 測試呼叫 coindesk API,並顯示其內容。
6. 測試呼叫資料轉換的 API,並顯示其內容。
