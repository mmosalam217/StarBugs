export class User{
    public id: string;
    public token: string;
    public clientId: string;
    public isAdmin: boolean;
    public isClient: boolean;
    public username: string;
    public firstName: string;
    public lastName: string;

    constructor( id: string, username: string, firstName: string, lastName: string,  token: string,  authorities:Array<any>,  clientId: string){
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.token = token;
        this.clientId = clientId;
        this.isAdmin = this.checkAdmin(authorities);
        this.isClient = this.checkClient(authorities);
    }


    checkAdmin(authorities: Array<any>){
        return authorities.filter(a => a.authority === 'SB_CLIENT_ADMIN_X004').length > 0 ? true: false;
    }

    checkClient(authorities: Array<any>){
        return authorities.filter(a => a.authority === 'SB_CLIENT_ROOT_X003').length > 0 ? true: false;

    }

}