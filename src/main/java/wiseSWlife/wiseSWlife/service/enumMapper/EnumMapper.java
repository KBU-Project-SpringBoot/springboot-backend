package wiseSWlife.wiseSWlife.service.enumMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wiseSWlife.wiseSWlife.model.vo.GraduationConditionEnum;

import java.util.LinkedHashMap;

@Configuration
public class EnumMapper {

    @Bean
    public EnumMapperFactory createEnumMapperFactory(){
        EnumMapperFactory enumMapperFactory = new EnumMapperFactory(new LinkedHashMap<>());
        enumMapperFactory.put("GraduationCondition", GraduationConditionEnum.class);
        return enumMapperFactory;
    }
}
