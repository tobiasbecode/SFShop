export class Address {

    public id: string;
    private addressName: string;
    private addressPLZ: number;
    private addressCity: string;
    private addressCountry: string;
    private addressMail: string;
    public username: string;


    constructor(id: string, addressName: string,
                addressPLZ: number, addressCity: string,
                addressCountry: string, addressMail: string,
                username: string ) {

        this.id = id;
        this.addressName = addressName;
        this.addressPLZ = addressPLZ;
        this.addressCity = addressCity;
        this.addressCountry = addressCountry;
        this.addressMail = addressMail;
        this.username = username;

    }
}
