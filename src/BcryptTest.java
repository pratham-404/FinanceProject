import org.mindrot.jbcrypt.*;

public class BcryptTest {
	public static void main(String[] args) {
		String pass = "adminpass123";
		
		System.out.println(BCrypt.hashpw(pass, BCrypt.gensalt()));
	}
}

//adminpass123
//$2a$10$RU.empeeFr.ks1PprvIE0eqsyaP/RF4smc7S/x5mXn4gvCCt5FK/O
