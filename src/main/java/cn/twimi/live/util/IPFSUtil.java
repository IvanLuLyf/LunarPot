package cn.twimi.live.util;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.IOException;
import java.util.List;

public class IPFSUtil {
    private static IPFS ipfs;

    private static IPFS getInstance() {
        if (ipfs == null) {
            ipfs = new IPFS("/dnsaddr/ipfs.infura.io/tcp/5001/https");
        }
        return ipfs;
    }

    public static String uploadFile(byte[] content) {
        NamedStreamable.ByteArrayWrapper wrapper = new NamedStreamable.ByteArrayWrapper(content);
        try {
            List<MerkleNode> result = getInstance().add(wrapper);
            return result.get(0).hash.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] fetchFile(String hash) {
        try {
            return getInstance().cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
