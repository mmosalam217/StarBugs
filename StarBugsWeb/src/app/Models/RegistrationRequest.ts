 import { Rootuser } from './Rootuser';
 import { CompanyInformation } from './CompanyInformation';

 export class RegistrationRequest{
     clientRootUser: Rootuser;
     clientCompany: CompanyInformation;
     subscriptionId: any;

    constructor(clientRootUser: Rootuser, clientCompany: CompanyInformation, subscriptionId: any){
        this.clientRootUser = clientRootUser;
        this.clientCompany = clientCompany;
        this.subscriptionId = subscriptionId;
    }
}