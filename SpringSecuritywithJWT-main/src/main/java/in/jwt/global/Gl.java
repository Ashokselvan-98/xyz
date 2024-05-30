package in.jwt.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Gl {
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Object> brandnotfound(IllegalArgumentException a) {
		return new ResponseEntity<>(a.getMessage(), HttpStatus.NOT_FOUND);

	}
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<Object> UsernameNotFound(UsernameNotFoundException a) {
		return new ResponseEntity<>(a.getMessage(), HttpStatus.NOT_FOUND);
		
	}

	
}
