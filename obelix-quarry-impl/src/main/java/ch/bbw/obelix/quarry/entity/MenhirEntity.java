package ch.bbw.obelix.quarry.entity;

import java.util.UUID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import ch.bbw.obelix.quarry.api.DecorativenessDto;
import ch.bbw.obelix.quarry.api.MenhirDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "menhirs")
@Setter
@Getter
@NoArgsConstructor
public class MenhirEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private Double weight;

	@Column(nullable = false)
	private String stoneType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Decorativeness decorativeness;

	@Column
	private String description;

	public MenhirDto toDto() {
		return new MenhirDto(id, weight, stoneType, DecorativenessDto.valueOf(decorativeness.name()), description);
	}

	public enum Decorativeness {
		PLAIN, SIMPLE, DECORATED, ORNATE, MASTERWORK
	}
}