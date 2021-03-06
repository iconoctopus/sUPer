package org.duckdns.spacedock.sUPer.presentation;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import org.duckdns.spacedock.sUPer.R;

/**
 * Created by iconoctopus on 6/7/16.
 */
public class HurtDialogFragment extends DialogFragment
{//TODO extraire une superclasse pour les fragments de cette application qui sont très proches les uns des autres
//TODO implémenter meilleure méthode de communication avec l'activité via une interface que l'activité implémentera et qui contiendra une méthode de callback. Actuellement dépendant de la classe spécifique de l'activité

    private FightBoard m_activity;
    private int m_index;
    private EditText m_DamageEditText;

    /**
     * listener du bouton OK de la boite de dialogue, appelle la métode de callback associée dans l'activité principale
     */
    private final DialogInterface.OnClickListener m_HurtDialogListener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int id)
        {
            //TODO: la ligne suivante bien que d'une concision admirable fait trop à la fois, la diviser pour plus de clarté
            m_activity.HurtInflictedCallback(m_index, Integer.parseInt(m_DamageEditText.getText().toString()));//on passe à l'activité les dégâts reçus
        }
    };

    /**
     * Véritable "constructeur" fournissant l'instance unique mais SURTOUT recevant des paramétres et permettant donc leur affectation à des variable membres dans OnCreateDialog()
     *
     * @param p_index
     * @return
     */
    static HurtDialogFragment getInstance(int p_index)
    {
        HurtDialogFragment frag = new HurtDialogFragment();
        Bundle args = new Bundle();
        args.putInt("index", p_index);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        m_activity = (FightBoard) getActivity();
        m_index = getArguments().getInt("index");

        //crée un builder pour construire la boite de dialogue et lui passe l'activité mère comme contexte avant de lui affecter un titre
        AlertDialog.Builder builder = new AlertDialog.Builder(m_activity);
        builder.setTitle(R.string.HurtDialogTitle);

        //inflate la vue de la boite de dialogue à partir du layout xml et l'affecte au builder
        View statSetupView = m_activity.getLayoutInflater().inflate(R.layout.fragment_hurt_dialog, null);//TODO: voir si il est possible de passer autre chose que null comme rootelement
        builder.setView(statSetupView);

        //récupère des pointeurs sur les widgets d'interraction présents dans la boite dialogue
        m_DamageEditText = (EditText) statSetupView.findViewById(R.id.DamageValue);

        builder.setCancelable(true);//ainsi on pourra faire back pour annuler

        //Passe au bouton positif son listener, le négatif est géré par le fait qu'il n'y a simplement rien à faire en ce cas
        builder.setPositiveButton(R.string.DialogModify, m_HurtDialogListener);
        builder.setNegativeButton(R.string.DialogCancelButton, null);

        //la méthode create produit la boite mais ne l'afiche pas, ce sera l'activité qui appellera show() sur ce fragment
        return (builder.create());
    }


}
