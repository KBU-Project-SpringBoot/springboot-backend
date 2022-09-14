import asyncio

from biblebot import IntranetAPI

async def main():
    resp = await IntranetAPI.GraduationExam.fetch(cookies={'ASP.NET_SessionId': '31wt31alaphdhh45skymsx45'})
    result = IntranetAPI.GraduationExam.parse(resp)
    responseData = dict(result.data.items())
    print(responseData)
asyncio.run(main())