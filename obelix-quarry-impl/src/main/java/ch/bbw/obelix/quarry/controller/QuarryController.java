package ch.bbw.obelix.quarry.controller;

import java.util.List;
import java.util.UUID;

import ch.bbw.obelix.quarry.api.MenhirDto;
import ch.bbw.obelix.quarry.api.QuarryApi;
import ch.bbw.obelix.quarry.service.QuarryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QuarryController implements QuarryApi {

	private final QuarryService quarryService;

	@Override
	public List<MenhirDto> getAllMenhirs() {
		return quarryService.getAllMenhirs();
	}

	@Override
	public MenhirDto getMenhirById(UUID menhirId) {
		return quarryService.getMenhirById(menhirId);
	}

	@Override
	public void deleteById(UUID menhirId) {
		quarryService.deleteById(menhirId);
	}

	@StandardException
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public static class UnknownMenhirException extends RuntimeException {}
}