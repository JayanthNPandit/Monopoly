package com.monopoly.board;

public class FakeBlock extends Block {

    protected FakeBlock(int aBlockNumber) {
        super(aBlockNumber);
    }

    public FakeBlock() {
        this(0);
        super.setBlockName("Fake Block");
        super.setBlockType(BlockTypeEnum.DUMMY_BLOCK);
    }

}