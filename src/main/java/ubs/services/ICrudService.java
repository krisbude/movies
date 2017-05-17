package ubs.services;

import java.util.Collection;

public interface ICrudService<T>{

	T create(T object);

	T read(Long id);

	Collection<T> readAll();

	void update(T object);

	void delete(Long id);

}