export class Ticket{
    public title: string;
    public issuerID: string;
    public issuerName: string;
    public clientID: string;
    public clientName: string;
    public projectID: string;
    public projectName: string;
    public app: string;
    public appName: string;
    public component: string;
    public componentName: string;
    public assignee: any;
    public status: string;
    public desc: string;
    public severity: string; 

    constructor(title: string, issuerID: string, issuerName: string,
                clientID: string, clientName: string, projectID: string, projectName: string,
                app: string, appName: string, component: string, componentName: string,
                assignee: any, status: string, desc: string, severity: string
                ){
                    this.title = title;
                    this.issuerID = issuerID;
                    this.issuerName = issuerName;
                    this.clientID = clientID;
                    this.clientName = clientName;
                    this.projectID = projectID;
                    this.projectName = projectName;
                    this.app = app;
                    this.appName = appName;
                    this.component = component;
                    this.componentName = componentName;
                    this.assignee = assignee;
                    this.status = status;
                    this.severity = severity;
                    this.desc = desc;

                }
}