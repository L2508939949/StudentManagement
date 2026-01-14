package raisetech.StudentManagement.controller;

public class ExceptionHandling extends RuntimeException {

  /**
   * ここでエラーが発生した際、エラーの内容を設定します。
   */

  public ExceptionHandling() {
    super();
  }

  public ExceptionHandling(String message) {
    super(message);
  }

  public ExceptionHandling(String message, Throwable cause) {
    super(message, cause);
  }

  public ExceptionHandling(Throwable cause) {
    super(cause);
  }

}