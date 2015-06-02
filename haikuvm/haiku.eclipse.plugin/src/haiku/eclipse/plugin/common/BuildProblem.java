package haiku.eclipse.plugin.common;

public class BuildProblem {

  private final String error;

  public BuildProblem(String error) {
    this.error = error;
  }

  public String getError() {
    return error;
  }
}
