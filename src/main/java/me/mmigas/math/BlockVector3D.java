package me.mmigas.math;

public final class BlockVector3D {

    private int x, y, z;

    public BlockVector3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockVector3D getMin(BlockVector3D other) {
        return new BlockVector3D(Math.min(x, other.getX()),Math.min(y, other.getY()),Math.min(z, other.getZ()));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }


}
