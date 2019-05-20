public interface CrudDao<T> {
    T get(@Param("id") String id);

    T getByEntity(T entity);

    List<T> findList(T entity,PageBounds pageBounds);

    Integer insert(T entity);

    Integer update(T entity);

    Integer delete(T entity);
}