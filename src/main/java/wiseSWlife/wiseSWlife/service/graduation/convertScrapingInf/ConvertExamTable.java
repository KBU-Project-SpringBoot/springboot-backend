package wiseSWlife.wiseSWlife.service.graduation.convertScrapingInf;

import wiseSWlife.wiseSWlife.model.graduation.ExamTable;

import java.util.Map;

public interface ConvertExamTable {
    Map<String, Boolean> convert(ExamTable table);
}
