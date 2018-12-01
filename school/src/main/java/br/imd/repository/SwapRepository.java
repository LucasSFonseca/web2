package br.imd.repository;
import org.springframework.stereotype.Repository;

import br.imd.model.Swap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SwapRepository extends JpaRepository<Swap, Integer> {

	@Query("SELECT p FROM Swap p WHERE p.userTo.id = :user_id or p.userFrom.id = :user_id")
    public List<Swap> findByUser(@Param("user_id") Integer userId);
}