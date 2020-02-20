import DateTimeFormat = Intl.DateTimeFormat;
// import ObjectID from "bson-objectid";
// import ObjectID from "bson-objectid";

export class Card{

  public id:string;
  public serialNumber:string;
  public location:any;
  public manufactured:DateTimeFormat;
  public validUntil:DateTimeFormat;
  public ctype:any;

  constructor() {}
}
