package de.lamp.cryptopanel;

import de.lamp.cryptopanel.repositories.InvoicesRepository;
import de.lamp.cryptopanel.repositories.InvoicesRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = CryptopanelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)

public class CryptopanelApplicationTests {

	@Autowired
	private InvoicesRepository invoicesRepository;

	@Test
	void contextLoads() {
	}

}
