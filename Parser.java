package os;

class Parser {

public Parser() {}
    
String commandName;
String[] args;
String [] console;
String [] commands = {
    "echo",
    "pwd",
    "cd",
    "ls",
    "ls -r",
    "mkdir",
    "rmdir",
    "touch",
    "cp",
    "cp -r",
    "rm",
    "cat"
};
int Spaces=0;
int index =0;
public boolean parse(String input){
     for(int i=0 ; i<input.length() ; i++)
    {
        if(input.charAt(i) == ' ')
        {
            Spaces ++;
        }
    }
    console = new String[Spaces+1];
    console = input.split(" ");
    if(Spaces == 0)
        commandName = input;
    else
    {
        commandName = console[index];
        index++;
        if(console[index].charAt(0) == '-' )
        {
            commandName = commandName + ' ' + console[index];
            index++;
            args = new String[Spaces-1];
        }
        else
        {
            args = new String[Spaces];
        }
        if(IsValid(commandName.trim()))
        {
            for(int i =0 ; i< args.length ; i++, index++)
            {
            args[i] = console[index];
            }
        }
        else
        {
            return false;
        }
    }
    return true;
 }
 
public boolean IsValid(String command)
{
    int flag =0;
       for (String command1 : commands) {
           if (command.equals(command1)) {
               flag++;
           }
       }
    if(flag == 0)
        return false;
    else
        return true;
}
 
public String getCommandName(){
    return commandName;
}

public String[] getArgs(){
    return args;
}
} 