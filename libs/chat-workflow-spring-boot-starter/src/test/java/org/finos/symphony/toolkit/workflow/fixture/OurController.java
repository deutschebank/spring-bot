package org.finos.symphony.toolkit.workflow.fixture;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.finos.symphony.toolkit.workflow.annotations.ChatVariable;
import org.finos.symphony.toolkit.workflow.content.CodeBlock;
import org.finos.symphony.toolkit.workflow.content.HashTag;
import org.finos.symphony.toolkit.workflow.content.Message;
import org.finos.symphony.toolkit.workflow.content.PastedTable;
import org.finos.symphony.toolkit.workflow.content.Room;
import org.finos.symphony.toolkit.workflow.content.User;
import org.finos.symphony.toolkit.workflow.content.Word;
import org.finos.symphony.toolkit.workflow.form.FormSubmission;
import org.finos.symphony.toolkit.workflow.java.Exposed;
import org.springframework.stereotype.Controller;

@Controller
public class OurController {
	
	public List<Object> lastArguments;
	public String lastMethod;
	
//	
//	// todo: do we need any other kinds of wildcards?
//	@Exposed("*")
//	public void listenToEverything(Message m) {
//		// guarav's reminder bot should do this - it needs to parse the date out of every
//		// message
//		lastArguments = Collections.singletonList(m);
//		lastMethod = "listenToEverything";
//	}
//	
//	@Exposed(value = "call", formClass=Person.class)
//	public void callPerson(Person arg) {
//		// do your own form processing
//		lastArguments = Collections.singletonList(arg);
//		lastMethod = "callPerson";
//	}
//	
//	@Exposed(value = "new claim", isButton = true, isMessage = false)
//	public void startNewClaim(StartClaim sc) {
//		// can't run without StartClaim, returns form to begin a process..
//		// user fills it in and this runs.
//		lastArguments = Collections.singletonList(sc);
//		lastMethod = "startNewClaim";
//	}
//	
//
//	@Exposed(value = "process", isButton = true, formName = "process-form")
//	public void processForm(FormSubmission f) {
//		// do your own form processing
//		// is this needed?
//		lastArguments = Collections.singletonList(f);
//		lastMethod = "processForm";
//	}
//	
//	
//	@Exposed("list") 
//	public void doCommand(Message m) {
//		// do something when user types in "/list"
//		lastArguments = Collections.singletonList(m);
//		lastMethod = "doCommand";
//	}
//
//	
//	@Exposed("show {user}") 
//	public void userDetails(@ChatVariable("user") User u) {
//		// provide some user details, e.g. /show @Rob Moffat
//		lastArguments = Collections.singletonList(u);
//		lastMethod = "userDetails";
//	}
//
//	@Exposed("process {sometable} {user}") 
//	public void process1(@ChatVariable("sometable") PastedTable t, @ChatVariable(required = false, value="user") User u) {
//		// provide some processing for a table.
//		lastArguments = Arrays.asList(t, u);
//		lastMethod = "process1";
//	}
//	
//
//	@Exposed("update {code}") 
//	public void process2(@ChatVariable("code") CodeBlock cb) {
//		// provide some processing for a block of code
//		lastArguments = Collections.singletonList(cb);
//		lastMethod = "process2";
//	}
	
	@Exposed({
		"add {user} to {hashtag}", 
		"add {user} {hashtag}"}) 
	public void addUserToTopic(@ChatVariable("user") User u, @ChatVariable("hashtag") HashTag t) {
		// provide some processing for a block of code
		lastArguments = Arrays.asList(u, t);
		lastMethod = "addUserToRoom";
	}
	
//	
//	@Exposed(admin = true, value = "delete {user}")
//	public void removeUserFromRoom(@ChatVariable("user") User u, Room r) {
//		lastArguments = Arrays.asList(u, r);
//		lastMethod = "removeUserFromRoom";	
//	}
//	
//	@Exposed(value="ban {word}")
//	public void banWord(@ChatVariable("word") Word w) {
//		lastArguments = Collections.singletonList(w);
//		lastMethod = "banWord";
//	}

}
