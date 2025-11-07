# Git Merge Scenarios - Complete Guide

*Compiled from top reputable sources: git-scm.com, Atlassian Git Tutorials, GitHub Docs, and industry best practices*

## Table of Contents

1. [Introduction](#introduction)
2. [Git Merge Fundamentals](#git-merge-fundamentals)
3. [Merge Types](#merge-types)
4. [Real-World Scenario with HelloWorldPlus Project](#real-world-scenario-with-helloworldplus-project)
5. [Step-by-Step Implementation](#step-by-step-implementation)
6. [Handling Merge Conflicts](#handling-merge-conflicts)
7. [Best Practices](#best-practices)
8. [Troubleshooting](#troubleshooting)

---

## Introduction

Git merge is the process of integrating independent lines of development created by `git branch` into a single branch. When working with distributed teams, multiple developers often work on different features simultaneously. Merging allows these separate development lines to be unified back into the main codebase.

### Key Concept
- **Merging combines sequences of commits** into one unified history
- **Non-destructive operation** - previous commits remain in history
- **Current branch receives changes** - target branch is not affected until explicitly merged
- **Merge commits are created** (except in fast-forward scenarios) to document the integration point

---

## Git Merge Fundamentals

### How Git Finds Common History

When you execute `git merge`, Git performs the following steps:

1. **Identifies the merge base** (common ancestor of two branches)
2. **Compares changes** between the base and each branch tip
3. **Combines the changes** if no conflicts exist
4. **Creates a merge commit** (except for fast-forward merges)

### The Three Components of a Merge

Git uses three commits to understand a merge:

```
Common Ancestor (merge base)
        |
        +---- Current Branch
        |
        +---- Merging Branch
```

**Example:**
```
    C4 (feature branch)
    |
A - B - C3 - C5 (main branch)
    |
    (common ancestor is B)
```

---

## Merge Types

### 1. Fast-Forward Merge

**When it occurs:**
- Linear history exists from current branch to target branch
- No divergent work to merge
- Current branch is a direct ancestor of the target branch

**Characteristics:**
- Git simply moves the pointer forward
- No merge commit is created
- Clean, linear history
- Fastest and simplest merge type

**Visual:**
```
Before:
  main ─→ A ─→ B
                └─→ C ─→ D ← feature

After:
  main ─────────→ A ─→ B ─→ C ─→ D
                          ↑ pointer moved
```

**When to use:**
- Small features or bug fixes
- Short-lived topic branches
- When you want a clean, linear history

**Avoid if:**
- You want to preserve a record of the branch integration
- You're working with long-running features
- Team requires explicit merge commits for audit trails

### 2. Three-Way Merge

**When it occurs:**
- Both branches have diverged from their common ancestor
- Both branches have new commits after the split point
- Linear fast-forward path doesn't exist

**Characteristics:**
- Git automatically creates a **merge commit**
- Merge commit has two parents (from both branches)
- Documents the integration point in history
- More complex but more informative than fast-forward

**Visual:**
```
Before:
        C4 ← feature (diverged branch)
        |
A - B - C3 - C5 ← main (both have new commits)

After:
        C4 ← feature
        |  \
A - B - C3 - C5 - M (merge commit)
             |  /
             (merging point)
```

**When to use:**
- Long-running feature branches
- Multiple developers working in parallel
- When you need to preserve branch information
- Production-ready features being integrated

### 3. Forced Non-Fast-Forward Merge (`--no-ff`)

**Characteristics:**
- Forces creation of merge commit even if fast-forward is possible
- Explicitly documents branch integration
- Creates merge commit for record-keeping

**Command:**
```bash
git merge --no-ff <branch-name>
```

**When to use:**
- Policy requirement for merge commits
- Audit trail and history preservation
- When you want every merge visible in the timeline

---

## Real-World Scenario with HelloWorldPlus Project

We'll use the HelloWorldPlus Java 21 project to demonstrate realistic git merge scenarios.

### Project Structure

```
helloworldplus/
├── pom.xml (Maven configuration)
├── src/
│   ├── main/java/com/example/
│   │   └── HelloWorldPlus.java (3 dummy methods)
│   └── test/java/com/example/
│       └── HelloWorldPlusTest.java (JUnit 5 tests)
└── .git/
```

### Scenario Overview

**Business Context:**
- Main branch contains stable, production-ready code
- Team is working on three parallel features:
  - **Feature A:** Add logging capability
  - **Feature B:** Enhance string utility method
  - **Hotfix:** Urgent bug in getGreeting() method

**Timeline:**
```
main     ──────→ C1 ────────────────────────→ M1 (merged hotfix) → C5 (final state)
                 |                            /
                 |                           /
hotfix           └────→ C2 ──────────────→ M1
                           (2-3 commits)

feature-logging  └────→ C3 ──→ C4 ──→ M2 (merged into main)
                  (3 commits)

feature-enhance  └────→ C6 ──────────→ C7 (3-way merge)
```

---

## Step-by-Step Implementation

### Phase 1: Initial Setup and Main Branch

#### Step 1.1: Create Initial Commit on Main

```bash
# Initialize repo (already done)
cd G:\investigation\git_merge\helloworldplus

# Stage all files
git add .

# Create initial commit
git commit -m "Initial commit: Hello World Plus project with JUnit 5 and Maven"

# Verify
git log --oneline
```

**Expected output:**
```
abc1234 Initial commit: Hello World Plus project with JUnit 5 and Maven
```

---

### Phase 2: Hotfix Branch (Urgent Production Fix)

#### Step 2.1: Create and Switch to Hotfix Branch

```bash
# Create hotfix branch from main
git checkout -b hotfix/critical-greeting-fix

# Verify you're on the hotfix branch
git branch
```

**Expected output:**
```
* hotfix/critical-greeting-fix
  main
```

#### Step 2.2: Make Changes to Fix the Issue

**Scenario:** The getGreeting() method needs urgent fix

**File: src/main/java/com/example/HelloWorldPlus.java**

```java
// Current implementation has a typo in greeting
public String getGreeting() {
    return "Welcome to HelloWorldPlus!"; // Contains typo - needs fix
}
```

**Change to:**
```java
// Fixed implementation
public String getGreeting() {
    return "Welcome to HelloWorldPlus - Production Ready!"; // Fixed message
}
```

#### Step 2.3: Commit the Fix

```bash
# Stage the changed file
git add src/main/java/com/example/HelloWorldPlus.java

# Commit with descriptive message
git commit -m "Fix: Correct getGreeting message for production

- Updated greeting text for clarity
- Fixes issue #42"

# View the commit
git log --oneline -n 3
```

**Expected output:**
```
def5678 Fix: Correct getGreeting message for production
abc1234 Initial commit: Hello World Plus project with JUnit 5 and Maven
```

#### Step 2.4: Update Tests for Hotfix

```bash
# Edit test file to match new greeting
# File: src/test/java/com/example/HelloWorldPlusTest.java
```

**Change:**
```java
@Test
@DisplayName("Test getGreeting method")
void testGetGreeting() {
    String result = app.getGreeting();
    assertEquals("Welcome to HelloWorldPlus - Production Ready!", result); // Updated
    assertNotNull(result);
}
```

```bash
# Stage and commit test update
git add src/test/java/com/example/HelloWorldPlusTest.java

git commit -m "Test: Update greeting test for production message"
```

---

### Phase 3: Merge Hotfix to Main (Fast-Forward Merge)

#### Step 3.1: Switch Back to Main

```bash
# Move back to main branch
git checkout main

# Verify you're on main
git status
```

**Expected output:**
```
On branch main
nothing to commit, working tree clean
```

#### Step 3.2: Merge Hotfix Branch (Fast-Forward)

```bash
# Merge hotfix into main
git merge hotfix/critical-greeting-fix

# View the result
git log --oneline --graph --all
```

**Expected output:**
```
* def5678 Test: Update greeting test for production message
* 2h9adef Fix: Correct getGreeting message for production
* abc1234 Initial commit: Hello World Plus project with JUnit 5 and Maven
```

**This is a FAST-FORWARD merge because:**
- main branch has NO new commits since hotfix was created
- Linear path exists from hotfix tip back to main
- Git simply moved the pointer forward

#### Step 3.3: Delete the Hotfix Branch

```bash
# Clean up the hotfix branch (no longer needed)
git branch -d hotfix/critical-greeting-fix

# Verify it's deleted
git branch -a
```

**Expected output:**
```
* main
```

---

### Phase 4: Create Feature Branch (Logging Enhancement)

#### Step 4.1: Create Feature Branch

```bash
# Create feature branch from current main
git checkout -b feature/add-logging

# Verify
git log --oneline -n 1
git branch
```

#### Step 4.2: Add Logging Capability (Commit 1)

**File: src/main/java/com/example/HelloWorldPlus.java**

```java
package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * HelloWorldPlus - A simple Java 21 application demonstrating Maven build with JUnit 5
 */
public class HelloWorldPlus {

    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        log("Application started");
        System.out.println("Hello World Plus!");
        HelloWorldPlus app = new HelloWorldPlus();
        log("Greeting: " + app.getGreeting());
        log("Sum result: " + app.calculateSum(5, 10));
        log("Reversed string: " + app.reverseString("Java21"));
        log("Application ended");
    }

    private static void log(String message) {
        System.out.println("[" + LocalDateTime.now().format(TIME_FORMATTER) + "] " + message);
    }

    public String getGreeting() {
        return "Welcome to HelloWorldPlus - Production Ready!";
    }

    public int calculateSum(int a, int b) {
        return a + b;
    }

    public String reverseString(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
```

```bash
# Commit the logging enhancement
git add src/main/java/com/example/HelloWorldPlus.java

git commit -m "Feature: Add logging capability to application

- Adds timestamp-formatted logging
- Logs application lifecycle events
- Uses Java 21 features"
```

#### Step 4.3: Add Logging Tests (Commit 2)

**File: src/test/java/com/example/HelloWorldPlusTest.java**

```java
@Test
@DisplayName("Verify logging does not affect functionality")
void testLoggingIntegration() {
    String result = app.getGreeting();
    assertNotNull(result);
    assertTrue(result.contains("Welcome"));
}
```

```bash
# Commit test updates
git add src/test/java/com/example/HelloWorldPlusTest.java

git commit -m "Test: Add logging integration tests"
```

#### Step 4.4: Documentation Update (Commit 3)

```bash
# Create README
echo "# HelloWorldPlus

This is a Java 21 Maven project demonstrating version control with Git.

## Features
- Hello World application
- Logging capability
- JUnit 5 tests
- OWASP security scanning

## Building
\`\`\`bash
mvn clean install
\`\`\`

## Running
\`\`\`bash
java -cp target/helloworldplus-1.0.0.jar com.example.HelloWorldPlus
\`\`\`" > README.md

# Add and commit
git add README.md

git commit -m "Docs: Add README with project information"
```

---

### Phase 5: Main Branch Updates (Concurrent Changes)

#### Step 5.1: Switch Back to Main

```bash
# Return to main branch
git checkout main

# Check current state
git log --oneline -n 2
```

**Expected output:**
```
def5678 Test: Update greeting test for production message
abc1234 Initial commit: Hello World Plus project with JUnit 5 and Maven
```

#### Step 5.2: Make Enhancement on Main (Creating Divergence)

**Scenario:** While feature-logging was in progress, another developer optimized the calculateSum method

**File: src/main/java/com/example/HelloWorldPlus.java**

```java
// Add method documentation and validation
public int calculateSum(int a, int b) {
    if (a < -1000000 || a > 1000000 || b < -1000000 || b > 1000000) {
        throw new IllegalArgumentException("Numbers must be within valid range");
    }
    return a + b;
}
```

```bash
# Commit the optimization
git add src/main/java/com/example/HelloWorldPlus.java

git commit -m "Enhancement: Add input validation to calculateSum

- Validates numbers are within acceptable range
- Throws IllegalArgumentException for invalid input
- Improves robustness"
```

**Now branches have diverged!**

```bash
# Visualize the divergence
git log --oneline --graph --all
```

**Expected output:**
```
* c4e9100 Enhancement: Add input validation to calculateSum
|
| * 1a2b3c4 Docs: Add README with project information
| * 5d6e7f8 Test: Add logging integration tests
| * 9g0h1i2 Feature: Add logging capability to application
|/
* def5678 Test: Update greeting test for production message
* abc1234 Initial commit: Hello World Plus project with JUnit 5 and Maven
```

---

### Phase 6: Three-Way Merge (Feature into Main)

#### Step 6.1: Prepare for Merge

```bash
# Ensure main branch is current
git checkout main

# Verify no uncommitted changes
git status

# Optional: Fetch latest from remote (in real scenario)
# git fetch origin
# git pull origin main
```

#### Step 6.2: Perform the Three-Way Merge

```bash
# Merge feature branch into main
git merge feature/add-logging

# View result
git log --oneline --graph --all -n 8
```

**Expected output:**
```
*   a1b2c3d Merge branch 'feature/add-logging' into main
|\
| * 1a2b3c4 Docs: Add README with project information
| * 5d6e7f8 Test: Add logging integration tests
| * 9g0h1i2 Feature: Add logging capability to application
* | c4e9100 Enhancement: Add input validation to calculateSum
|/
* def5678 Test: Update greeting test for production message
* abc1234 Initial commit: Hello World Plus project with JUnit 5 and Maven
```

**Key observations:**
- **Merge commit created** (a1b2c3d) - NOT a fast-forward
- **Two parents** visible in the graph
- **Both histories preserved** in the commit graph
- **Branch point documented** for future reference

#### Step 6.3: Verify Merge Success

```bash
# Check status
git status

# View the merged code
git log --oneline main -n 1

# Verify files from feature branch are present
ls README.md
```

#### Step 6.4: Clean Up Feature Branch

```bash
# Delete the feature branch
git branch -d feature/add-logging

# Verify deletion
git branch
```

---

### Phase 7: Create Second Feature Branch

#### Step 7.1: Create Feature Branch

```bash
# Create new feature branch from current main
git checkout -b feature/enhance-string-utils

# Make changes to reverseString method
# File: src/main/java/com/example/HelloWorldPlus.java
```

#### Step 7.2: Enhance String Utility

```java
public String reverseString(String input) {
    if (input == null || input.isEmpty()) {
        return input;
    }
    return new StringBuilder(input).reverse().toString();
}
```

```bash
# Commit enhancement
git add src/main/java/com/example/HelloWorldPlus.java

git commit -m "Enhancement: Improve reverseString null safety

- Handle null and empty string cases
- Return original if input invalid
- Prevents unexpected behavior"
```

#### Step 7.3: Add Tests

```bash
# Update test file with new test cases
# File: src/test/java/com/example/HelloWorldPlusTest.java
```

```java
@Test
@DisplayName("Test reverseString with null safety")
void testReverseStringNullSafety() {
    String result = app.reverseString(null);
    assertNull(result);
}

@Test
@DisplayName("Test reverseString with empty string")
void testReverseStringEmpty() {
    String result = app.reverseString("");
    assertEquals("", result);
}
```

```bash
# Commit test updates
git add src/test/java/com/example/HelloWorldPlusTest.java

git commit -m "Test: Add null safety tests for reverseString"
```

---

### Phase 8: Merge Second Feature with `--no-ff` (Explicit Merge Commit)

#### Step 8.1: Switch to Main and Merge

```bash
# Return to main
git checkout main

# Merge with --no-ff flag to force merge commit
git merge --no-ff feature/enhance-string-utils -m "Merge: String utility enhancements into main

This merge integrates:
- Null safety checks for reverseString
- Improved error handling
- Additional test coverage

Resolves: task#123"
```

#### Step 8.2: Review Merge Result

```bash
# View the graph
git log --oneline --graph --all -n 15

# Note the explicit merge commit created
```

**Expected output:**
```
*   f5g6h7i Merge: String utility enhancements into main
|\
| * 2j3k4l5 Test: Add null safety tests for reverseString
| * 6m7n8o9 Enhancement: Improve reverseString null safety
|/
*   a1b2c3d Merge branch 'feature/add-logging' into main
|\
| * 1a2b3c4 Docs: Add README with project information
| * 5d6e7f8 Test: Add logging integration tests
| * 9g0h1i2 Feature: Add logging capability to application
* | c4e9100 Enhancement: Add input validation to calculateSum
|/
* def5678 Test: Update greeting test for production message
* abc1234 Initial commit: Hello World Plus project with JUnit 5 and Maven
```

#### Step 8.3: Clean Up

```bash
# Delete the feature branch
git branch -d feature/enhance-string-utils

# Verify the branch is gone
git branch
```

---

## Handling Merge Conflicts

### What Causes Merge Conflicts?

Conflicts occur when:
- **Same file modified** on both branches at **same location**
- Git cannot automatically decide which version to use
- Manual resolution required

### Conflict Scenario Example

**Situation:** Two developers modify the same method

**Main branch version:**
```java
public int calculateSum(int a, int b) {
    return a + b;
}
```

**Feature branch version:**
```java
public int calculateSum(int a, int b) {
    log("Calculating sum of " + a + " and " + b);
    return a + b;
}
```

### Step-by-Step Conflict Resolution

#### Step 1: Identify Conflicts

```bash
# Attempt merge (conflicts occur)
git merge feature/conflicting-changes

# Check status
git status
```

**Expected output:**
```
On branch main
You have unmerged paths.
  (fix conflicts and run "git commit")

Unmerged paths:
  (use "git add <file>..." to mark resolution)
    both modified: src/main/java/com/example/HelloWorldPlus.java

no changes added to commit but you are still merging.
```

#### Step 2: View Conflict Markers

```bash
# Open the conflicted file
cat src/main/java/com/example/HelloWorldPlus.java
```

**Expected output:**
```java
public int calculateSum(int a, int b) {
<<<<<<< HEAD
    return a + b;
=======
    log("Calculating sum of " + a + " and " + b);
    return a + b;
>>>>>>> feature/conflicting-changes
}
```

**Conflict Markers Explained:**
- `<<<<<<< HEAD` - Start of conflict from current branch (main)
- `=======` - Separator between versions
- `>>>>>>> feature/conflicting-changes` - End of conflict from merging branch

#### Step 3: Resolve Conflicts

**Options to resolve:**

**Option A: Keep Current Branch (main) Version**
```java
public int calculateSum(int a, int b) {
    return a + b;
}
```

**Option B: Keep Merging Branch (feature) Version**
```java
public int calculateSum(int a, int b) {
    log("Calculating sum of " + a + " and " + b);
    return a + b;
}
```

**Option C: Combine Both Versions (RECOMMENDED)**
```java
public int calculateSum(int a, int b) {
    log("Calculating sum of " + a + " and " + b);
    return a + b;
}
```

#### Step 4: Mark as Resolved and Complete

```bash
# Stage the resolved file
git add src/main/java/com/example/HelloWorldPlus.java

# Verify resolution
git status

# Complete the merge with a commit
git commit -m "Resolve: Merge conflicts in calculateSum method

Combined logging from feature branch with main branch implementation"

# View final state
git log --oneline --graph -n 5
```

### Using Merge Tools (Visual Resolution)

```bash
# Configure merge tool
git config merge.tool vimdiff

# Launch visual merge tool for conflicts
git mergetool

# Git will open the configured tool for each conflicted file
# After resolving, save and exit
# Complete merge with git commit
```

---

## Best Practices

### 1. Commit Discipline

**✅ DO:**
```bash
# Atomic commits with single responsibility
git commit -m "Feature: Add logging capability

- Implements timestamp formatting
- Logs application lifecycle events"

# Clear, descriptive commit messages
git commit -m "Fix: Correct null pointer in calculateSum"

# Reference issue trackers
git commit -m "Fix: Resolve issue #42 in getGreeting"
```

**❌ DON'T:**
```bash
# Vague messages
git commit -m "Updates"

# Multiple unrelated changes
git commit -m "Fixed bug and added feature and updated docs"

# Unclear context
git commit -m "Changed stuff"
```

### 2. Branch Naming Conventions

**Standard Convention:**
```
<type>/<brief-description>
```

**Types:**
- `feature/` - New functionality
- `bugfix/` - Bug fixes
- `hotfix/` - Urgent production fixes
- `refactor/` - Code refactoring
- `test/` - Test additions
- `docs/` - Documentation

**Examples:**
```bash
git checkout -b feature/add-logging
git checkout -b bugfix/fix-calculation-error
git checkout -b hotfix/critical-security-patch
git checkout -b refactor/simplify-merging-logic
```

### 3. Pre-Merge Checklist

```bash
# 1. Ensure you're on the correct receiving branch
git status

# 2. Pull latest changes from remote
git fetch origin
git pull origin main

# 3. Verify no uncommitted changes
git status

# 4. Run tests on current branch
mvn clean test

# 5. Review branch to be merged
git log main..feature/branch-name

# 6. Proceed with merge
git merge feature/branch-name
```

### 4. Merge Strategy Selection

| Scenario | Strategy | Command |
|----------|----------|---------|
| Small feature/bugfix | Fast-forward | `git merge <branch>` |
| Long-running feature | 3-way merge | `git merge <branch>` |
| Audit trail required | No fast-forward | `git merge --no-ff <branch>` |
| Clean history needed | Squash | `git merge --squash <branch>` |
| Reapply commits | Rebase | `git rebase <branch>` |

### 5. Code Review Before Merging

**Process:**
```bash
# Push branch to remote
git push -u origin feature/add-logging

# Create Pull Request on GitHub/GitLab/Bitbucket
# - Describe changes
# - Reference related issues
# - Request reviewers

# Reviewers approve before merge
# - Code quality check
- Functionality verification
- Test coverage review

# Merge after approval
```

### 6. Clean History Practices

```bash
# View history before cleaning
git log --oneline -n 10

# Option 1: Squash commits before merging (on feature branch)
git rebase -i main  # Interactive rebase

# Option 2: Squash during merge
git merge --squash feature/branch

# Option 3: Fast-forward for small features
git merge feature/branch  # Ensures linear history
```

### 7. Merge Commit Messages

**For 3-way merges, use descriptive messages:**

```bash
git merge feature/add-logging -m "Merge: Add logging capability into main

Introduces:
- Timestamp-formatted log output
- Application lifecycle logging
- LocalDateTime formatting

Tested with:
- JUnit 5 test suite
- Manual integration testing

Related: issue #45"
```

---

## Troubleshooting

### Issue 1: Accidental Merge or Wrong Branch

**Problem:** Merged into wrong branch
```bash
git merge feature/wrong-branch  # Oops! Merged into wrong branch
```

**Solution:**
```bash
# Option 1: Undo the merge (if not yet pushed)
git reset --hard HEAD~1

# Option 2: If pushed, create a revert commit
git revert -m 1 <merge-commit-sha>

# Option 3: Cherry-pick to correct branch
git checkout correct-branch
git cherry-pick <specific-commits>
```

### Issue 2: Merge Conflict in Binary Files

**Problem:** Conflict in non-text file (JAR, image, etc.)

**Solution:**
```bash
# Use theirs version (from merging branch)
git checkout --theirs path/to/file

# Or use ours version (from current branch)
git checkout --ours path/to/file

# Stage and commit
git add path/to/file
git commit -m "Resolve: Use correct version of binary file"
```

### Issue 3: Merge Conflicts Too Complex

**Problem:** Too many conflicts to resolve manually

**Solution:**
```bash
# Abort the merge attempt
git merge --abort

# Alternative: Use mergetool
git mergetool

# Or rebase for cleaner history (advanced)
git rebase --onto main feature/branch
```

### Issue 4: Lost Commits After Merge

**Problem:** Can't find specific commits after merge

**Solution:**
```bash
# View all commits ever (including deleted references)
git reflog

# Recover lost commits
git cherry-pick <lost-commit-sha>

# Or reset to previous state
git reset --hard <sha-from-reflog>
```

### Issue 5: Slow Merge Performance

**Problem:** Merge taking very long time

**Solution:**
```bash
# Check file count difference
git diff --name-only main feature/branch | wc -l

# Use specific merge strategy
git merge -s recursive -X theirs feature/branch

# Or rebase for better performance with many commits
git rebase main
```

---

## Advanced Scenarios

### Scenario 1: Merge Only Specific Commits

```bash
# Cherry-pick specific commits instead of full merge
git cherry-pick <commit-sha>

# Cherry-pick range of commits
git cherry-pick <start-sha>..<end-sha>

# Useful when:
# - You need only some commits from a branch
# - Feature branch has mixed commits
# - Need partial merge without full branch integration
```

### Scenario 2: Merge with Squashing

```bash
# Combine all commits into one
git merge --squash feature/branch

# Manually commit
git commit -m "Feature: Add new capability (squashed)"

# Useful when:
# - Feature has messy commit history
# - Want clean single commit
# - Large feature branch with many small commits
```

### Scenario 3: Selective Merge (Not All Changes)

```bash
# Merge specific file from branch
git checkout feature/branch -- path/to/specific/file

# Stage and commit
git add path/to/specific/file
git commit -m "Merge: Bring in specific file from feature branch"
```

### Scenario 4: Rollback a Merge

```bash
# Create revert commit (safe for pushed merges)
git revert -m 1 <merge-commit-sha>

# Push revert
git push origin main

# Useful when:
# - Merge was pushed to remote
# - Want to preserve history
# - Need audit trail
```

---

## Complete Merge Workflow Summary

```bash
# 1. Create feature branch
git checkout -b feature/new-feature

# 2. Make changes and commits
echo "code changes" > file.txt
git add file.txt
git commit -m "Feature: Add new functionality"

# 3. Prepare main branch
git checkout main
git pull origin main

# 4. Perform merge
git merge feature/new-feature

# 5. If conflicts occur
# - Resolve manually in editor
# - OR use mergetool
git mergetool

# 6. Complete merge
git add .
git commit

# 7. Verify merge
git log --oneline --graph --all -n 10

# 8. Push to remote
git push origin main

# 9. Delete feature branch
git branch -d feature/new-feature
git push origin --delete feature/new-feature
```

---

## Quick Reference: Common Git Merge Commands

```bash
# List branches
git branch

# Create feature branch
git checkout -b feature/name

# Switch branch
git checkout main

# Merge branch into current branch
git merge feature/name

# Force merge commit (no fast-forward)
git merge --no-ff feature/name

# Merge with squashing
git merge --squash feature/name

# Abort merge if conflicts arise
git merge --abort

# View merge history
git log --merges --oneline

# View branches that are merged
git branch --merged

# View branches NOT yet merged
git branch --no-merged

# Delete branch
git branch -d feature/name

# Delete remote branch
git push origin --delete feature/name

# View detailed merge info
git log --graph --oneline --all

# View differences between branches
git diff main feature/name
```

---

## Conclusion

Git merge is a powerful tool for integrating parallel development efforts. Understanding the different merge types—fast-forward, three-way, and forced merges—allows you to choose the right strategy for your workflow.

### Key Takeaways:

1. **Fast-forward merges** maintain clean linear history for small features
2. **Three-way merges** document integration points and preserve branch information
3. **Merge conflicts** are manageable and a normal part of collaborative development
4. **Best practices** include atomic commits, clear branch naming, and thorough code review
5. **Commit discipline** makes merge history clear and maintainable

### Resources Used:

- **git-scm.com**: Official Git documentation and Pro Git book
- **Atlassian Git Tutorials**: Comprehensive merge strategies and workflows
- **GitHub Docs**: Git basics and collaborative development models
- **Industry Best Practices**: Real-world merge scenarios and team workflows

---

**Document Version:** 1.0  
**Last Updated:** November 2025  
**Project:** HelloWorldPlus (Java 21 Maven Project)
