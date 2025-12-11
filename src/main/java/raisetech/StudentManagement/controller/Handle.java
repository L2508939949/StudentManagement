package raisetech.StudentManagement.controller;

public class Handle  extends  RuntimeException{

  /**
   * ここでエラーが発生した際、エラーの内容を設定します。
   */

  public Handle() {
    super();
  }

  public Handle(String message) {
    super(message);
  }

  public Handle(String message, Throwable cause) {
    super(message, cause);
  }

  public Handle(Throwable cause) {
    super(cause);
  }

  {


}
