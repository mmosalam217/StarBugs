export class CompanyInformation{
    name: string;
    region: string;
    domain: string;

    constructor(name: string, region: string, domain: string){
        this.name = name;
        this.region = region;
        this.domain = domain;
    }
}