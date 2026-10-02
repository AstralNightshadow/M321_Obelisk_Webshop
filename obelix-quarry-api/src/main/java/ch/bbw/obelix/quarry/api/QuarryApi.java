package ch.bbw.obelix.quarry.api;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;

public interface QuarryApi {

	@GetExchange("/api/menhirs")
	List<MenhirDto> getAllMenhirs();

	@GetExchange("/api/menhirs/{menhirId}")
	MenhirDto getMenhirById(@PathVariable UUID menhirId);

	@DeleteExchange("/api/quarry/{menhirId}")
	void deleteById(@PathVariable UUID menhirId);
}
