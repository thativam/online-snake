package src;

public class Snake {
    int[] snakexLength = new int[750];
    int[] snakeyLength = new int[750];

    int lengthOfSnake;
    int moves;

    boolean left;
    boolean right;
    boolean up;
    boolean down;

    boolean death;

    public Snake(){
        this.left=false;
        this.right=false;
        this.up=false;
        this.down=false;
        this.death=false;
        this.lengthOfSnake=5;
        this.moves=0;
    }

    public void moveRight(){
        if (this.moves != 0 && !this.death) {
                this.moves++;
                if (!this.left) {
                    this.right = true;
                } else {
                    this.right = false;
                    this.left = true;
                }
                this.up = false;
                this.down = false;
        }
    }

    public void moveLeft(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.right) {
                this.left = true;
            } else {
                this.left = false;
                this.right = true;
            }
            this.up = false;
            this.down = false;
        }
    }

    public void moveUp(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.down) {
                this.up = true;
            } else {
                this.up = false;
                this.down = true;
            }
            this.left = false;
            this.right = false;
        }
    }

    public void moveDown(){
        if (this.moves != 0 && !this.death) {
            this.moves++;
            if (!this.up) {
                this.down = true;
            } else {
                this.down = false;
                this.up = true;
            }
            this.left = false;
            this.right = false;
        }
    }

    public void dead() {
        this.right = false;
        this.left = false;
        this.up = false;
        this.down = false;
        this.death = true;
    }

    public void movementRight(){
        if (this.moves != 0 && !this.death) {
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                this.snakeyLength[i + 1] = this.snakeyLength[i];
            }
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                if (i == 0) {
                    this.snakexLength[i] = this.snakexLength[i] + 6;
                } else {
                    this.snakexLength[i] = this.snakexLength[i - 1];
                }
                if (this.snakexLength[0] > 524) {
                    this.snakexLength[0] -= 6;
                    dead();
                }
            }
        }
    }

    public void movementLeft(){
        if (this.moves != 0 && !this.death) {
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                this.snakeyLength[i + 1] = this.snakeyLength[i];
            }
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                if (i == 0) {
                    this.snakexLength[i] = this.snakexLength[i] - 6;
                } else {
                    this.snakexLength[i] = this.snakexLength[i - 1];
                }
                if (this.snakexLength[0] < 25) {
                    this.snakexLength[0] += 6;
                    dead();
                }
            }
        }
    }

    public void movementUp(){
        if (this.moves != 0 && !this.death) {
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                this.snakexLength[i + 1] = this.snakexLength[i];
            }
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                if (i == 0) {
                    this.snakeyLength[i] = this.snakeyLength[i] - 6;
                } else {
                    this.snakeyLength[i] = this.snakeyLength[i - 1];
                }
                if (this.snakeyLength[0] < 73) {
                    this.snakeyLength[0] += 6;
                    dead();
                }
            }
        }
    }

    public void movementDown(){
        if (this.moves != 0 && !this.death) {
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                this.snakexLength[i + 1] = this.snakexLength[i];
            }
            for (int i = this.lengthOfSnake - 1; i >= 0; i--) {
                if (i == 0) {
                    this.snakeyLength[i] = this.snakeyLength[i] + 6;
                } else {
                    this.snakeyLength[i] = this.snakeyLength[i - 1];
                }
                if (this.snakeyLength[0] > 568) {
                    this.snakeyLength[0] -= 6;
                    dead();
                }
            }
        }
    }
}