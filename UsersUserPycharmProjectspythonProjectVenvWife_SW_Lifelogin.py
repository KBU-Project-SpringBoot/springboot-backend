import asyncio

from biblebot import IntranetAPI

async def main():
    resp = await IntranetAPI.TotalAcceptanceStatus.fetch(cookies={'ASP.NET_SessionId': 'xu1lnhv53wgzmg45uhbmtlrv'})
    result = IntranetAPI.TotalAcceptanceStatus.parse(resp)
    responseData = dict(result.data.items())
    print(responseData)
asyncio.run(main())