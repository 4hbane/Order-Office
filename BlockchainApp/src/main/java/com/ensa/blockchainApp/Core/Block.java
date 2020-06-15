package com.ensa.blockchainApp.Core;

import com.ensa.blockchainApp.Business.Reclamation;
import com.ensa.blockchainApp.Repositories.BlockRepository;
import lombok.Data;
import org.hibernate.Session;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Date;
import java.util.Iterator;

@Entity
@Data
public class Block {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private final String hash;
    @NotNull
    private final String prevBlockHash;
    @NotNull
    private long time;
    @Lob
    @NotNull
    private Reclamation data;



    public Block() {
        prevBlockHash = Sha256Hash.ZERO_HASH.toString();
        hash = Sha256Hash.ZERO_HASH.toString();
        time = new Date().getTime();
        data = new Reclamation("Genesis Block");
        calculateHash();
    }

    public Block(Reclamation data, String prevBlockHash) {
        this.prevBlockHash = prevBlockHash;
        this.data = data;
        time = new Date().getTime();
        hash = calculateHash();
    }

    public String calculateHash() {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(prevBlockHash.getBytes());
            stream.write(Sha256Hash.hash(data.toString ().getBytes())); // I changed data.getReclamation().getBytes() to that !
            uint32ToByteStream(stream, time);
            return Sha256Hash.wrap(Sha256Hash.hash(stream.toByteArray())).toString();
        } catch (IOException e) {
            throw new RuntimeException(e); // Cannot happen.
        }
    }


    public static void addGenesisBlockToDatabase() {
        BlockRepository blockRepository;
        Block b = new Block();

    }

   /* public static void addBlockToDatabase(Session s, String data) {
        Query q = s.createQuery("SELECT hash FROM Block ORDER BY id DESC");
        q.setMaxResults(1);
        String prevBlockHash = q.uniqueResult().toString();
        Block b = new Block(data, prevBlockHash) ;
        Transaction tx = s.beginTransaction();
        s.save(b);
        tx.commit();
        s.close();
    }*/

    public static boolean VerifySuccessiveBlock(Block current, Block prev) {
        if(!(current.getPrevBlockHash().equals(prev.getHash()))) return false;
        if(!(current.calculateHash().equals(current.getHash()))) return false;
        return true;
    }

    public static boolean VerifyChain(Session s) {
        // TODO(): Finish up this part.
        List<Block> blocks = s.createQuery("SELECT a FROM Block a ORDER BY id ASC", Block.class).getResultList();
        Iterator i = blocks.iterator();
        Block prev = (Block) i.next();
        Block current = (Block) i.next();
        if(!VerifySuccessiveBlock(current, prev)) return false;
        while(i.hasNext()) {
            prev = current;
            current = (Block) i.next();
            if(!VerifySuccessiveBlock(current, prev)) return false;
        }
        return true;
    }

    // Writes long to stream of bytes.
    public static void uint32ToByteStream(OutputStream stream, long val) throws IOException {
        stream.write((int) (0xFF & val));
        stream.write((int) (0xFF & (val >> 8)));
        stream.write((int) (0xFF & (val >> 16)));
        stream.write((int) (0xFF & (val >> 24)));
    }
}
