# GitHub setup and submission steps

This guide assumes Windows because the project can be developed with IntelliJ IDEA or Eclipse on Windows.

## 1. Create a GitHub account

1. Open https://github.com/
2. Select **Sign up**.
3. Register with your email address.
4. Verify the email address.
5. Complete the account setup.

Choose a simple professional GitHub username because the repository link will be shared with the recruiter.

## 2. Install Git

Install Git for Windows from:

https://git-scm.com/download/win

After installation, open PowerShell or Git Bash and run:

```bash
git --version
```

You should see a Git version.

## 3. Configure Git once

Use the same name/email you want associated with your commits:

```bash
git config --global user.name "Your Name"
git config --global user.email "your-email@example.com"
```

Check:

```bash
git config --global --list
```

## 4. Create an empty GitHub repository

On GitHub:

1. Click **New repository**.
2. Repository name: `student-management-system`
3. Add a short description such as `Spring Boot REST API for student, course and enrollment management`.
4. Select **Public** only if the assignment instructions allow a public repository. Otherwise use **Private**.
5. Do not add a README, `.gitignore`, or license during repository creation because this project already contains them.
6. Click **Create repository**.

Keep the GitHub page open. GitHub will show the repository URL.

## 5. Open the project folder

In PowerShell:

```powershell
cd C:\path	o\student-management-system
```

Replace the path with the actual location where you saved this project.

Check the files:

```powershell
dir
```

You should see `pom.xml`, `README.md`, `src`, and `postman`.

## 6. Initialize Git

```bash
git init
git branch -M main
git status
```

## 7. Make the first commit

```bash
git add .
git status
git commit -m "Initial student management system implementation"
```

The `git status` command before committing is useful because it lets you verify that secrets, IDE files and database files are not being committed.

## 8. Connect the local project to GitHub

Copy the HTTPS repository URL from GitHub. It will look like:

```text
https://github.com/YOUR_USERNAME/student-management-system.git
```

Then run:

```bash
git remote add origin https://github.com/YOUR_USERNAME/student-management-system.git
git remote -v
```

Do not type the example URL literally. Replace `YOUR_USERNAME`.

## 9. Push the code

```bash
git push -u origin main
```

GitHub may ask you to authenticate in a browser or through Git Credential Manager.

If GitHub asks for a password in an old-style terminal prompt, do not use your GitHub account password. Use GitHub's supported authentication flow, such as Git Credential Manager or a personal access token.

## 10. Verify the repository

Refresh the GitHub repository page.

Confirm that these are visible:

- `src/main/java/...`
- `src/main/resources/application.properties`
- `pom.xml`
- `README.md`
- `GITHUB_SETUP.md`
- `postman/Student-Management-System.postman_collection.json`
- `.gitignore`

Confirm that these are NOT visible:

- `target/`
- `data/`
- `.idea/`
- passwords or production secrets
- personal files

## 11. If you make changes later

Use:

```bash
git status
git add .
git commit -m "Add student profile and enrollment APIs"
git push
```

Keep commit messages meaningful. Avoid making dozens of meaningless commits.

## 12. Final repository link

Your submission link will be:

```text
https://github.com/YOUR_USERNAME/student-management-system
```

Before submitting it, open the link in an incognito/private browser window if the repository is public, or verify the reviewer has access if it is private.
