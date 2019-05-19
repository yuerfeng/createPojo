package ${DAO_PACKAGE};
import ${POJO_PACKAGE}.${CLASS_NAME};
public interface ${CLASS_NAME}Dao extends CrudDao<${CLASS_NAME}> {
    ${CLASS_NAME} get(@Param("id") String id);

    ${CLASS_NAME} getByEntity(${CLASS_NAME} entity));

    List<${CLASS_NAME}> findList(${CLASS_NAME} entity,PageBounds pageBounds);

    Integer insert(${CLASS_NAME} entity);

    Integer update(${CLASS_NAME} entity);

    Integer delete(${CLASS_NAME} entity);
}