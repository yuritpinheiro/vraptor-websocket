package br.com.caelum.vraptor.controller;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.com.caelum.vraptor.view.Results;

import java.util.ArrayList;
import java.util.List;

@Controller
@ApplicationScoped
public class IndexController {

	private final Result result;

	private static List<Person> table;

	/**
	 * @deprecated CDI eyes only
	 */
	protected IndexController() {
		this(null);
	}
	
	@Inject
	public IndexController(Result result) {
		this.result = result;
		table = new ArrayList<>();
		table.add(new Person(0, "Mary","Jane",800.00));
		table.add(new Person(1, "Peter","Paker",99.99));
		table.add(new Person(2, "Bruce","Wayne",9999999999.99));
		table.add(new Person(3, "Tony","Stark",10000000000.00));
		table.add(new Person(4, "Kal","El",0.0));
		table.add(new Person(5, "Jor","El",0.0));
		table.add(new Person(6, "Irmão","do Jorel",10.00));
		table.add(new Person(7, "Alfred","Pennyworth",100.00));
		table.add(new Person(8, "Son","Goku",50.00));
		table.add(new Person(9, "Son","Gohan",25.00));
	}

	@Path("/")
	public void index() {
		result.include("variable", "VRaptor!");
	}

	@Path("/table")
	@Get
	public void getTable() {
		result.use(Results.json()).withoutRoot().from(table).serialize();
	}

	@Path("/remove/person")
	@Delete
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void removePerson(Person p) {
		table.remove(p);
		TestWebSocket.remove(p);
		result.nothing();
	}
}