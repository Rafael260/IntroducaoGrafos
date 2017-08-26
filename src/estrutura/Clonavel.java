package estrutura;

public interface Clonavel<T extends Clonavel<T>> extends Cloneable {
	T clone();
}
