package vbo.sharenurturebackend.snb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vbo.sharenurturebackend.snb.model.FbGroup;

import java.util.List;
import java.util.Optional;

@Repository
public interface FbGroupRepository extends CrudRepository<FbGroup, String> {

    Optional<FbGroup> findByName(String groupName);

    List<FbGroup> findAll();
}
