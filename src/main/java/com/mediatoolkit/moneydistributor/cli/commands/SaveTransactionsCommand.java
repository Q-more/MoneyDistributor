package com.mediatoolkit.moneydistributor.cli.commands;

import com.mediatoolkit.moneydistributor.client.RestClient;
import com.mediatoolkit.moneydistributor.api.model.TransactionRequest;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

public class SaveTransactionsCommand implements Command {

    private final RestClient restClient;

    public SaveTransactionsCommand(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Transaction:
     * (lender ,  description)
     * <p>
     * borrower
     * amount
     *
     * @param args
     */
    @Override
    public void execute(List<String> args) {
        if (args.size() < 3) {
            System.out.println("Invalid number of parameters. Given params: " + args);
            return;
        }

        Long lender = Long.parseLong(args.get(0));
        args.remove(0);
        String description = args.get(0);
        args.remove(0);

        List<TransactionRequest.TransactionEntry> entries = new ArrayList<>();
        args.forEach(a -> {
            String[] arguments = a.split("=");
            if (arguments.length != 2) {
                System.out.println("Invalid structure of (borrower_id=amount)");
                return;
            }
            entries.add(new TransactionRequest.TransactionEntry(Long.parseLong(arguments[0]), Double.parseDouble(arguments[1])));
        });

        try {
            restClient.saveTransaction(new TransactionRequest(lender, description, entries));
        } catch (HttpClientErrorException e) {
            System.out.println(e.getResponseBodyAsString());
        }

    }

    @Override
    public String getDescription() {
        return "Save transaction between two users.";
    }

    @Override
    public String getExample() {
        return "SAVE_TRANSACTIONS lender_id, description, (borrower_id=amount)+ ";
    }

    @Override
    public String getName() {
        return "SAVE_TRANSACTIONS";
    }
}
