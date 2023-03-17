package wiseSWlife.wiseSWlife.service.enumMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wiseSWlife.wiseSWlife.dto.graduationConditionEnumMapper.GraduationConditionEnumMapperType;
import wiseSWlife.wiseSWlife.dto.graduationConditionEnumMapper.GraduationConditionEnumMapperValue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Getter
//@AllArgsConstructor
//public class EnumMapperFactory {
//
//    // 다양한 종류의 Enum을 생성 및 관리하는 factory
//    private Map<String, List<GraduationConditionEnumMapperValue>> factory;
//
//    //새로운 Enum 종류를 추가하는 함수
//    public void put(String key, Class<? extends GraduationConditionEnumMapperType> e){
//        factory.put(key, toEnumValues(e));
//    }
//
//    //특정 Enum의 항목들을 조회하는 함수
//    public List<GraduationConditionEnumMapperValue> get(String key){
//        return factory.get(key);
//    }
//
//    private List<GraduationConditionEnumMapperValue> toEnumValues(Class<? extends GraduationConditionEnumMapperType> e){
//        return Arrays.stream(e.getEnumConstants()).map(GraduationConditionEnumMapperValue::new)
//                .collect(Collectors.toList());
//    }
//}
