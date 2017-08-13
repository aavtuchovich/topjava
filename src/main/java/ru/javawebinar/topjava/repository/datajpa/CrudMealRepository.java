package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
	@Transactional
	@Override
	Meal save(Meal meal);

	@Transactional
	@Modifying
	@Query(value = Meal.DELETE, nativeQuery = true)
	boolean delete(@Param("id") int id, @Param("userId") int userId);

	Meal get(int id, int userId);

	@Query(value = Meal.ALL_SORTED,nativeQuery = true)
	List<Meal> getAll(@Param("userId") int userId);

	@Query(value = Meal.GET_BETWEEN,nativeQuery = true)
	List<Meal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);
}
