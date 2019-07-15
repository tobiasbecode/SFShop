export class User {

    constructor(
        public username: string,
        private token: string ) {
    }

    get _token() {
        return this.token;
    }
}
