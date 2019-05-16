package ${POJO_PACKAGE};
import java.io.Serializable;
import java.util.Date;
public class ${CLASS_NAME} implements Serializable{
    <#list FIELDS as FIELD>
        private ${FIELD.javaType} ${FIELD.property};
    </#list>
}