export class Product {
  [x: string]: any;

    public name: string;
    public price: number;
    public category: string;
    public description: string;
    public offer: boolean;
    public shippingCosts: number;
    public inventory: number;
    public uuid: string;
    public id: number;
    public imagePath: string;
    public amount: number;

    constructor(name: string, price: number,
                category: string, description: string, offer: boolean, 
                shippingCosts: number, inventory: number, uuid: string, 
                id: number, imagePath: string, amount: number
                ) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.offer = offer;
        this.shippingCosts = shippingCosts;
        this.inventory = inventory;
        this.uuid = uuid;
        this.id = id;
        this.imagePath = imagePath;
        this.amount = amount;
    }

}