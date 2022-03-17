package os;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Terminal  {
    Parser parser;

    public Terminal(Parser parser){
    this.parser = parser;
    }
     
    String path = System.getProperty("user.dir");//default path as a starter
    File trm = new File(path);

public void echo(String[] s1){
    try
    {
    for(String s : s1)
      System.out.print(s + " ");
    }
    catch(Exception e)
    {
        System.out.print("Nothing to print");
    }
}

public void cd(String[] s1)
{
    try
    {
    if(s1 == null){
        trm = new File(System.getProperty("user.dir"));
    }
    else if("..".equals(s1[0]))
    {
        try
        {
            trm = new File(trm.getParent()) ;
            path = trm.getAbsolutePath();
            
        }
        catch(Exception e)
        {
            System.out.println("you can't back any more");
        }
    }
        
    else
    {
        if(trm.exists())
            System.out.print("");
        if(!trm.isAbsolute())
            System.out.println("Invalid path");
        trm = new File(trm.getAbsolutePath()+"\\"+ s1[0]);
        path = trm.getAbsolutePath();
    }
    }
    catch(Exception e)
    {
     System.out.println("not valid path");
     trm = new File(System.getProperty("use.dir"));
    }       
}

public void touch(String[] s1) throws IOException
{
    try
    {
    File dir = new File(s1[0] + ".txt");
    boolean createNewFile = dir.createNewFile();
    }
    catch(Exception e)
    {
        System.out.println("You can not attach files here");
    }
}

public void cd(){
    trm = new File(System.getProperty("user.dir"));
}

public File checkValidPath(String path)
{
    File f = new File(path);
        if(f.isAbsolute())
        {
            return f;
        }
        else
        {
            f = new File(trm.getAbsolutePath() + "\\" + path);
            return f;
        }
}

public void mkdir(String[] a)
{
 for(int i=0 ; i<a.length ; i++)
 {
    File newF = new File(checkValidPath(a[i]).getAbsolutePath());
    if(newF.mkdir())
        System.out.println("directory is created");
    else
        System.out.println("error");
 }
}

public void pwd() {
  System.out.println(trm.getAbsolutePath());
}

public void r() throws FileNotFoundException, IOException{
    String content[] = trm.list();//listing dir's content(folders, .txt, etc.)
    Arrays.sort(content);
    for(int i=content.length-1;i>=0;i--)//printing in reversed order
        System.out.println(content[i]);    
}

public void ls() throws FileNotFoundException, IOException{
        String content[] = trm.list();//listing dir's content(folders, .txt, etc.)
        Arrays.sort(content);
        for(int i=0;i<content.length;i++)//printing in alphabetical order
            System.out.println(content[i]);
}

public void rmdir(String[] x) throws IOException{
    String[] p=x;//get the path
try {
    if(p[0].equals("*")){
    File dir = new File(trm.getAbsolutePath());
    {
    FileFilter fileFiltering = new FileFilter() {//filtering directories(Takes only folders)
        public boolean accept(File f) {
            return f.isDirectory();//stored only if it's a directory
        }
    };
    File[] arrOfFiles  = dir.listFiles(fileFiltering);//array of files contains the listed directories in the current path
    for (File currentFile : arrOfFiles)
    {
        String filePath=currentFile.getPath();
        if (!Files.list(Paths.get(currentFile.getPath())).findAny().isPresent()){//to tell wether the dir is empty or not
            Files.delete(Paths.get(filePath));
        }}}}
    else{
        Files.delete(Paths.get(p[0]));
    }

} catch (IOException ex) {   
 System.out.println("Directory neither exist nor empty!");
}
}

public void cp(String [] a) throws FileNotFoundException, IOException{
    FileInputStream in = null;
    FileOutputStream out = null;
    String from = path + "\\" + a[0];
    String to = path + "\\" + a[1];
    in = new FileInputStream(from);
    out = new FileOutputStream(to);
    File file = checkValidPath(path);
    byte d[] = new byte[(int)file.length()];
    in.read(d);
    out.write(d);
    out.flush();
    in.close();
    out.close();
}

public void cpr(File src, File dest){
    if (src.isDirectory()){
        if (!dest.exists()){
            dest.mkdirs();
        }
        String files[] = src.list();
        for (String file : files){
            File srcFile = new File(src, file);
            File destFile = new File(dest, file);
            cpr(srcFile, destFile);
        }
    }
    else{
        InputStream in = null;
        OutputStream out = null;
        try {
	        in = new FileInputStream(src);
	        out = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = in.read(buffer)) > 0){
	        	out.write(buffer, 0, length);
	        }
	        in.close();
	        out.close();
        }
        catch(Exception e){
        	System.out.println(e);
        }
    }
}

public void rm(String [] a){
	File file = checkValidPath(path + "\\" + a[0]);
	if(file.delete()){
		System.out.println(file.getName() + " Deleted");
	} else {
		System.out.println("File Not Found");
	}
}

public void cat(String [] a) throws FileNotFoundException, IOException{
	for(int i = 0; i < a.length; i++) {
		File file = new File(path + "\\" + a[i]);
                System.out.println(path + "\\" + a[i]);
		if (file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(path + "\\" + a[i]));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
		}
		else {
		    System.out.println(file.getName() + " doesn't exist");
		}
	}
}

public void chooseCommandAction() throws IOException{
    if("pwd".equals(parser.getCommandName())){
        pwd();
    }
    else if("ls".equals(parser.getCommandName())){
        ls();
    }
    else if ("ls -r".equals(parser.getCommandName())){
        r();
    }
    else if("rmdir".equals(parser.getCommandName())){  
        rmdir(parser.getArgs());
    }
    else if("echo".equals(parser.getCommandName()))
    {
        echo(parser.getArgs());
    }
    else if("mkdir".equals(parser.getCommandName()))
    {
        mkdir(parser.getArgs());
    }
    else if("cd".equals(parser.getCommandName()))
    {
        if(parser.getArgs() == null)
            cd();
        else
            cd(parser.getArgs());
    }
    else if("touch".equals(parser.getCommandName()))
    {
        touch(parser.getArgs());
    }
    else if("cp".equals(parser.getCommandName()))
    {
        cp(parser.getArgs());
    }
    else if("cp -r".equals(parser.getCommandName()))
    {
    	String srcPath = path + "\\" + parser.getArgs()[0];
    	String trgtPath = path + "\\" + parser.getArgs()[1];
        File srcDir = new File(srcPath);
        File trgtDir = new File(trgtPath);
        cpr(srcDir, trgtDir);
    }
    else if("rm".equals(parser.getCommandName()))
    {
        rm(parser.getArgs());
    }
    else if("cat".equals(parser.getCommandName()))
    {
        cat(parser.getArgs());
    }
    else
    {
        System.out.println("Unknown command , please enter a valid command");
    }
}

static class Parser {

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


public static void main(String[] args) throws IOException{     
    Scanner in=new Scanner (System.in);
    String command;
    Parser p=new Parser(); 
    boolean parse; 
    Terminal t=new Terminal(p);
    t.parser=p;    
    command=in.nextLine();
    while(!command.equals("exit")){
            Parser z=new Parser();
            t.parser=z;
            parse = z.parse(command);
            t.chooseCommandAction();
            System.out.println();
            command=in.nextLine();
    }
    } 
}