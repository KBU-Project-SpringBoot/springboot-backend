import asyncio

from biblebot import IntranetAPI

async def main():
    resp = await IntranetAPI.Login.fetch("kimjy011", "samsung0115")
    result = IntranetAPI.Login.parse(resp)
    cookie = result.data["cookies"]
    resp = await IntranetAPI.Profile.fetch(cookies=cookie)
    result = IntranetAPI.Profile.parse(resp)
    responseData = dict(cookie.items()|result.data.items())
    responseData["intCookie"] = responseData.pop("ASP.NET_SessionId")
    print(responseData)
asyncio.run(main())