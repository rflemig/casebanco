import org.junit.Assert;
import org.mockito.Mockito;

import com.controlecontas.model.Cliente;
import com.controlecontas.repository.ClienteRepository;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * InsertClienteTest.
 */
public class InsertClienteTest {

	private Cliente cliente;
	private ClienteRepository clienteRepository;

	/**
	 * Parametriza o Cliente
	 */
	@Given("^Parametriza o Cliente")
	public void configureImageAndRepository() {

		clienteRepository = Mockito.mock(ClienteRepository.class);
		cliente = new Cliente();
		cliente.setNome("Nome Teste");

	}

	/**
	 * Salva o cliente 'mockado'
	 */
	@When("^Salva o cliente mockado")
	public void saveImage() {

		Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);

	}

	/**
	 * Verifica se tem nome no Cliente salvo
	 */
	@Then("^Verifica se tem nome no Cliente salvo")
	public void verifyTypeValue() {

		boolean temValor = !"".equals(cliente.getNome());

		Assert.assertEquals(temValor, true);

	}
}