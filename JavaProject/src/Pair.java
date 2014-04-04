@SuppressWarnings("hiding")
public class Pair<String, integer> {

  private String left;
  private int right;

  public Pair(String left, int right) {
    this.left = left;
    this.right = right;
  }

  public String getLeft() { return left; }
  public int getRight() { return right; }

  public void setLeft(String newLeft) { left = newLeft; }
  public void setRight(int newRight) { right = newRight; }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof Pair)) return false;
    @SuppressWarnings("rawtypes")
	Pair pairo = (Pair) o;
    return this.left.equals(pairo.getLeft()) &&
           this.right == pairo.getRight();
  }

}