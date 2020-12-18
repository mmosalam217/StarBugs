export class Workspace{
     clientId: string;
     name: string;
     projects: any[];

    constructor(clientId: string, name: string, projects: any[]){
        this.clientId = clientId;
        this.name = name;
        this.projects = projects;
    }

  
}