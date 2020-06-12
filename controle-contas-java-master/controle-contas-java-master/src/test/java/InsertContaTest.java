import org.junit.Assert;
import org.mockito.Mockito;

import com.controlecontas.model.Conta;
import com.controlecontas.repository.ContaRepository;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * InsertContaTest.
 */
public class InsertContaTest {

	private Conta conta;
	private ContaRepository contaRepository;

	/**
	 * Parametriza o Conta
	 */
	@Given("^Parametriza o Conta")
	public void configureImageAndRepository() {

		contaRepository = Mockito.mock(ContaRepository.class);
		conta = new Conta();
		conta.setNome("Nome Teste");

	}

	/**
	 * Salva o conta 'mockado'
	 */
	@When("^Salva o conta mockado")
	public void saveImage() {

		Mockito.when(contaRepository.save(conta)).thenReturn(conta);

	}

	/**
	 * Verifica se tem nome no Conta salvo
	 */
	@Then("^Verifica se tem nome no Conta salvo")
	public void verifyTypeValue() {

		boolean temValor = !"".equals(conta.getNome());

		Assert.assertEquals(temValor, true);

	}
}