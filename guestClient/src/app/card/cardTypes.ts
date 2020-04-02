export class CardTypes {
  public static types:Array<String> = ["PERSONNEL", "GUEST"];

  construct () {
//     types.push("PERSONNEL");
//     types.push("GUEST");
  }

  public static getTypes() {
    return CardTypes.types;
  }
}
