package ch.bbw.obelix.quarry.repository;

import java.util.UUID;

import ch.bbw.obelix.quarry.entity.MenhirEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenhirRepository extends JpaRepository<MenhirEntity, UUID> {}