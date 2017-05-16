package ubs.services;

public interface ICrudService<T>{

	Long create(T object);

	T read(Long id);

	void update(T object);

	void delete(Long id);

}